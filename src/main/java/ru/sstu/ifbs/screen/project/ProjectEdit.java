package ru.sstu.ifbs.screen.project;

import io.jmix.core.*;
import io.jmix.ui.Fragments;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.model.InstanceLoader;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.project.securityinfo.ProjectSecurityInfoMapper;
import ru.sstu.ifbs.entity.project.securityinfo.gis.GisSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.IspdnSecurityInfo;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.screen.actualthreat.ActualThreatEdit;
import ru.sstu.ifbs.screen.ispdnsecurityinfo.IspdnSecurityInfoFragment;
import ru.sstu.ifbs.screen.project.securityinfo.gissecurityinfo.GisSecurityInfoFrag;
import ru.sstu.ifbs.serivce.project.ThreatMatchingService;

import java.util.List;

@UiController("gwf_Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {

    @Autowired
    private GroupBoxLayout securityInfoContainer;
    @Autowired
    private Fragments fragments;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private ComboBox<ProjectSecurityInfoMapper> projectSecurityInfoMapper;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private Metadata metadata;
    @Autowired
    private Messages messages;
    @Autowired
    private InstanceLoader<Project> projectDl;
    @Autowired
    private ThreatMatchingService threatMatchingService;
    @Autowired
    private InstanceContainer<Project> projectDc;
    @Autowired
    private CollectionPropertyContainer<ActualThreat> actualThreatsDc;
    @Autowired
    private FetchPlans fetchPlans;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Project> event) {
        projectSecurityInfoMapper.setValue(ProjectSecurityInfoMapper.GIS_SECURITY_INFO);
        onSecurityInfoChange(ProjectSecurityInfoMapper.GIS_SECURITY_INFO);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        projectDl.load();
        var secInfo = getEditedEntity().getSecurityInfo();
        if (secInfo instanceof GisSecurityInfo gis) {
            projectSecurityInfoMapper.setValue(ProjectSecurityInfoMapper.GIS_SECURITY_INFO);
            showGisFrag(gis, true);
        } else if (secInfo instanceof IspdnSecurityInfo ispdn) {
            projectSecurityInfoMapper.setValue(ProjectSecurityInfoMapper.ISPDN_SECURITY_INFO);
            showIspdnFrag(ispdn, true);
        }
    }

    @Subscribe("projectSecurityInfoMapper")
    public void onProjectSecurityInfoMapperValueChange(HasValue.ValueChangeEvent<ProjectSecurityInfoMapper> event) {
        if (event.getPrevValue() == event.getValue() || !event.isUserOriginated()) return;

        onSecurityInfoChange(event.getValue());
    }

    private void onSecurityInfoChange(ProjectSecurityInfoMapper mapper) {
        switch (mapper) {
            case ISPDN_SECURITY_INFO -> {
                var ispdn = dataContext.create(IspdnSecurityInfo.class);
                getEditedEntity().setSecurityInfo(ispdn);
                showIspdnFrag(ispdn, false);
            }
            case GIS_SECURITY_INFO -> {
                var gis = dataContext.create(GisSecurityInfo.class);
                getEditedEntity().setSecurityInfo(gis);
                showGisFrag(gis, false);
            }
        }
    }

    private void showGisFrag(GisSecurityInfo entity, boolean needReload) {
        var frag = fragments.create(this, GisSecurityInfoFrag.class);
        var dc = frag.getGisSecurityInfoDc();
        if (needReload) {
            entity = dataContext.merge(dataManager.load(Id.of(entity))
                                               .fetchPlan(dc.getFetchPlan())
                                               .one());
        }
        dc.setItem(entity);
        drawFrag(frag.getFragment(), messageTools.getEntityCaption(metadata.getClass(entity)));
    }

    private void showIspdnFrag(IspdnSecurityInfo entity, boolean needReload) {
        var frag = fragments.create(this, IspdnSecurityInfoFragment.class);
        var dc = frag.getIspdnSecurityInfoDc();
        if (needReload) {
            entity = dataContext.merge(dataManager.load(Id.of(entity))
                                               .fetchPlan(dc.getFetchPlan())
                                               .one());
        }
        dc.setItem(entity);
        drawFrag(frag.getFragment(), messageTools.getEntityCaption(metadata.getClass(entity)));
    }

    private void drawFrag(Fragment fragment, String captionKey) {
        securityInfoContainer.removeAll();
        securityInfoContainer.add(fragment);
        securityInfoContainer.setCaption(messages.getMessage(captionKey));
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

        actualThreats.forEach(it -> actualThreatsDc.getMutableItems().add(dataContext.merge(it)));
    }
}