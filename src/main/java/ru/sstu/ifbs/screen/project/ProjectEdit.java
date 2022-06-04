package ru.sstu.ifbs.screen.project;

import io.jmix.core.*;
import io.jmix.ui.Fragments;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.ActualSecurityMeasure;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.ProjectSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ProjectSecurityInfoType;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.IspdnSecurityInfo;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.repository.GisSecurityInfoRepository;
import ru.sstu.ifbs.repository.IspdnSecurityInfoRepository;
import ru.sstu.ifbs.repository.SecurityMeasureRepository;
import ru.sstu.ifbs.screen.actualthreat.ActualThreatEdit;
import ru.sstu.ifbs.screen.ispdnsecurityinfo.IspdnSecurityInfoFrag;
import ru.sstu.ifbs.screen.project.securityinfo.gissecurityinfo.GisSecurityInfoFrag;
import ru.sstu.ifbs.screen.securitymeasure.SecurityMeasureBrowse;
import ru.sstu.ifbs.serivce.project.SecurityMeasuresMatchingService;
import ru.sstu.ifbs.serivce.project.ThreatMatchingService;
import ru.sstu.ifbs.serivce.project.ThreatScenarioMatchingService;

import javax.inject.Named;
import java.util.HashSet;
import java.util.List;

import static io.jmix.ui.screen.OpenMode.*;
import static java.util.Comparator.comparing;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.*;

@UiController("gwf_Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {

    @Autowired
    private GroupBoxLayout securityInfoContainer;
    @Autowired
    private Fragments fragments;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private ThreatMatchingService threatMatchingService;
    @Autowired
    private CollectionPropertyContainer<ActualThreat> actualThreatsDc;
    @Autowired
    private FetchPlans fetchPlans;
    @Autowired
    private InstanceLoader<Project> projectDl;
    @Autowired
    private InstanceContainer<Project> projectDc;
    @Autowired
    private EntityStates entityStates;
    @Autowired
    private IspdnSecurityInfoRepository ispdnSecurityInfoRepository;
    @Autowired
    private GisSecurityInfoRepository gisSecurityInfoRepository;
    @Autowired
    private ThreatScenarioMatchingService threatScenarioMatchingService;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private CollectionPropertyContainer<ActualSecurityMeasure> actualMeasuresDc;
    @Autowired
    private SecurityMeasureRepository securityMeasureRepository;
    @Autowired
    private SecurityMeasuresMatchingService securityMeasuresMatchingService;

    private ProjectSecurityInfo secInfo;
    @Named("actualMeasuresTable.generate")
    private BaseAction actualMeasuresTableGenerate;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        projectDl.load();
    }

    @Subscribe("securityInfoType")
    public void onSecurityInfoTypeValueChange(HasValue.ValueChangeEvent<ProjectSecurityInfoType> event) {
        if (event.getValue() == event.getPrevValue()) {
            return;
        }
        if (secInfo != null) {
            dataContext.remove(secInfo);
        }
        showSecurityInfo();
    }

    @Subscribe("actualThreatsTable.generate")
    public void onActualThreatsTableGenerate(Action.ActionPerformedEvent event) {
        var project = getEditedEntity();
        var projectThreats = project.getActualThreats().stream().map(ActualThreat::getThreat).collect(toSet());

        threatMatchingService.getMatches(
                        project,
                        fetchPlans.builder(Threat.class).addFetchPlan(FetchPlan.BASE).build(),
                        fetchPlans.builder(ThreatScenario.class).addFetchPlan(FetchPlan.BASE).build())
                .stream()
                .filter(not(it -> projectThreats.contains(it.getThreat())))
                .map(dataContext::merge)
                .forEach(actualThreatsDc.getMutableItems()::add);
    }

    @Subscribe("actualThreatsTable.actualize")
    public void onActualThreatsTableActualize(Action.ActionPerformedEvent event) {
        var project = getEditedEntity();
        var projectThreats = project.getActualThreats().stream()
                .collect(toMap(
                        ActualThreat::getThreat,
                        it -> it
                ));
        var actualScenarios = threatScenarioMatchingService.getMatches(
                project,
                fetchPlans.builder(ThreatScenario.class).addFetchPlan(FetchPlan.BASE).build());

        actualScenarios.forEach((k, v) -> projectThreats.get(k).setScenarios(v));

        actualThreatsDc.setItems(
                projectThreats.values().stream()
                        .filter(it -> it.getScenarios().size() > 0)
                        .collect(toList()));
    }

    @Subscribe("actualMeasuresTable.generate")
    public void onActualMeasuresTableGenerate(Action.ActionPerformedEvent event) {
        var measures = getGeneratedActualSecurityMesaure();
        var usedMeasures = actualMeasuresDc.getItems().stream()
                .map(ActualSecurityMeasure::getValue)
                .collect(toSet());
        measures.stream()
                .filter(not(it -> usedMeasures.contains(it.getValue())))
                .forEach(actualMeasuresDc.getMutableItems()::add);
        sortMeasures();
    }

    @Subscribe("actualMeasuresTable.add")
    public void onActualMeasuresTableAdd(Action.ActionPerformedEvent event) {
        var lookup = screenBuilders.lookup(SecurityMeasure.class, this)
                .withOpenMode(THIS_TAB)
                .withScreenClass(SecurityMeasureBrowse.class)
                .withSelectHandler(list -> {
                    list.stream()
                            .map(it -> {
                                var asm = dataContext.create(ActualSecurityMeasure.class);
                                asm.setProject(getEditedEntity());
                                asm.setValue(it);
                                return asm;
                            })
                            .forEach(actualMeasuresDc.getMutableItems()::add);
                    sortMeasures();
                })
                .build();

        var usedMeasures = actualMeasuresDc.getMutableItems().stream()
                .map(ActualSecurityMeasure::getValue)
                .collect(toSet());
        var unusedMeasures = new HashSet<>(securityMeasureRepository.getAll(
                projectDc.getFetchPlan()
                        .getProperty("actualMeasures").getFetchPlan()
                        .getProperty("value").getFetchPlan()));
        unusedMeasures.removeAll(usedMeasures);

        lookup.getSecurityMeasuresDc().setItems(unusedMeasures);
        lookup.show();
    }

    private List<ActualSecurityMeasure> getGeneratedActualSecurityMesaure() {
        var project = getEditedEntity();
        var secMesFP = projectDc.getFetchPlan()
                .getProperty("actualMeasures").getFetchPlan()
                .getProperty("value").getFetchPlan();
        if (secInfo instanceof IspdnSecurityInfo ispdnInfo) {
            return securityMeasuresMatchingService.getMatches(project, ispdnInfo, secMesFP);
        } else if (secInfo instanceof GisSecurityInfo gisInfo) {
            return securityMeasuresMatchingService.getMatches(project, gisInfo, secMesFP);
        } else {
            throw new IllegalStateException();
        }

    }

    private boolean isActualMeasuresTableGenerateShouldBeEnable() {
        if (secInfo instanceof IspdnSecurityInfo ispdnInfo) {
            return ispdnInfo.getPersonalData().getProtectionLevel() != null;
        }
        if (secInfo instanceof GisSecurityInfo gisInfo) {
            return gisInfo.getSecurityClass() != null;
        }
        return false;
    }

    @Install(to = "actualThreatsTable.create", subject = "screenConfigurer")
    private void actualThreatsTableCreateScreenConfigurer(ActualThreatEdit screen) {
        screen.init(getEditedEntity());
    }

    @Install(to = "actualThreatsTable.edit", subject = "screenConfigurer")
    private void actualThreatsTableEditScreenConfigurer(ActualThreatEdit screen) {
        screen.init(getEditedEntity());
    }

    private void sortMeasures() {
        actualMeasuresDc.getMutableItems().sort(ActualSecurityMeasure.COMPARATOR);
    }

    private void showSecurityInfo() {
        var item = projectDc.getItem();
        var isNew = entityStates.isNew(item);
        switch (item.getSecurityInfoType()) {
            case ISPDN_SECURITY_INFO -> {
                var frag = fragments.create(this, IspdnSecurityInfoFrag.class);
                var dc = frag.getIspdnSecurityInfoDc();
                var sec = isNew ? createIspdnSecInfo() : ispdnSecurityInfoRepository.getByProject(item, dc.getFetchPlan());
                this.secInfo = sec;
                dc.setItem(dataContext.merge(sec));
                drawFrag(frag);
            }
            case GIS_SECURITY_INFO -> {
                var frag = fragments.create(this, GisSecurityInfoFrag.class);
                var dc = frag.getGisSecurityInfoDc();
                var sec = isNew ? createGisSecInfo() : gisSecurityInfoRepository.getByProject(item, dc.getFetchPlan());
                this.secInfo = sec;
                dc.setItem(dataContext.merge(sec));
                drawFrag(frag);
            }
        }
        actualMeasuresTableGenerate.setEnabled(isActualMeasuresTableGenerateShouldBeEnable());
    }

    private void drawFrag(ScreenFragment fragment) {
        securityInfoContainer.removeAll();
        securityInfoContainer.add(fragment.getFragment());
    }

    private IspdnSecurityInfo createIspdnSecInfo() {
        var ispdn = dataContext.create(IspdnSecurityInfo.class);
        ispdn.setProject(projectDc.getItem());
        return ispdn;
    }

    private GisSecurityInfo createGisSecInfo() {
        var gis = dataContext.create(GisSecurityInfo.class);
        gis.setProject(projectDc.getItem());
        return gis;
    }
}