package ru.sstu.ifbs.screen.project;

import io.jmix.core.*;
import io.jmix.ui.Fragments;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Fragment;
import io.jmix.ui.component.GroupBoxLayout;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.IspdnSecurityInfo;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.repository.GisSecurityInfoRepository;
import ru.sstu.ifbs.repository.IspdnSecurityInfoRepository;
import ru.sstu.ifbs.screen.actualthreat.ActualThreatEdit;
import ru.sstu.ifbs.screen.ispdnsecurityinfo.IspdnSecurityInfoFrag;
import ru.sstu.ifbs.screen.project.securityinfo.gissecurityinfo.GisSecurityInfoFrag;
import ru.sstu.ifbs.serivce.project.ThreatMatchingService;

import java.util.HashSet;

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

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        projectDl.load();
        showSecurityInfo();
    }

    private void showSecurityInfo() {
        var item = projectDc.getItem();
        var isNew = entityStates.isNew(item);
        switch (item.getSecurityInfoType()) {
            case ISPDN_SECURITY_INFO -> {
                var frag = fragments.create(this, IspdnSecurityInfoFrag.class);
                var dc = frag.getIspdnSecurityInfoDc();
                var sec = isNew ? createIspdnSecInfo() : ispdnSecurityInfoRepository.getByProject(item, dc.getFetchPlan());
                dc.setItem(dataContext.merge(sec));
                drawFrag(frag.getFragment());
            }
            case GIS_SECURITY_INFO -> {
                var frag = fragments.create(this, GisSecurityInfoFrag.class);
                var dc = frag.getGisSecurityInfoDc();
                var sec = isNew ? createGisSecInfo() : gisSecurityInfoRepository.getByProject(item, dc.getFetchPlan());
                dc.setItem(dataContext.merge(sec));
                drawFrag(frag.getFragment());
            }
        }
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

    @Install(to = "actualThreatsTable.create", subject = "screenConfigurer")
    private void actualThreatsTableCreateScreenConfigurer(ActualThreatEdit screen) {
        screen.init(getEditedEntity());
    }

    @Install(to = "actualThreatsTable.edit", subject = "screenConfigurer")
    private void actualThreatsTableEditScreenConfigurer(ActualThreatEdit screen) {
        screen.init(getEditedEntity());
    }

    @Subscribe("actualThreatsTable.generate")
    public void onActualThreatsTableGenerate(Action.ActionPerformedEvent event) {
        var project = getEditedEntity();
        var actualThreats = threatMatchingService.getMatches(
                project,
                fetchPlans.builder(Threat.class).addFetchPlan(FetchPlan.BASE).build(),
                fetchPlans.builder(ThreatScenario.class).addFetchPlan(FetchPlan.BASE).build());

        var usedThreats = new HashSet<>(actualThreatsDc.getItems());
        var unusedThreats = new HashSet<>(actualThreats);
        unusedThreats.removeAll(usedThreats);
        unusedThreats.forEach(it -> actualThreatsDc.getMutableItems().add(dataContext.merge(it)));
    }

    private void drawFrag(Fragment fragment) {
        securityInfoContainer.removeAll();
        securityInfoContainer.add(fragment);
    }
}