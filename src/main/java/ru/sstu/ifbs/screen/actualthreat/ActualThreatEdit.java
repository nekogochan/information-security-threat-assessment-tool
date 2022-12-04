package ru.sstu.ifbs.screen.actualthreat;

import io.jmix.core.Messages;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.action.Action;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.ActualThreat;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.repository.ThreatRepository;
import ru.sstu.ifbs.repository.ThreatScenarioRepository;
import ru.sstu.ifbs.screen.threatscenario.ThreatScenarioBrowse;

import static io.jmix.ui.Notifications.NotificationType.WARNING;
import static io.jmix.ui.screen.OpenMode.DIALOG;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;

@UiController("gwf_ActualThreat.edit")
@UiDescriptor("actual-threat-edit.xml")
@EditedEntityContainer("actualThreatDc")
public class ActualThreatEdit extends StandardEditor<ActualThreat> {

    @Autowired
    private ThreatRepository threatRepository;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private CollectionContainer<Threat> unusedThreatsDc;
    @Autowired
    private InstanceContainer<ActualThreat> actualThreatDc;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private ThreatScenarioRepository threatScenarioRepository;
    @Autowired
    private CollectionPropertyContainer<ThreatScenario> scenariosDc;

    private Project project;

    public void init(Project project) {
        this.project = project;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        requireNonNull(project, "Project is null, maybe forgot to call 'init' method");
        var usedThreatIds = project.getActualThreats().stream()
                .map(ActualThreat::getThreat)
                .map(DefaultEntity::getId)
                .toList();
        unusedThreatsDc.setItems(
                threatRepository.getByIdsExcluded(
                        usedThreatIds,
                        actualThreatDc.getFetchPlan().getProperty("threat").getFetchPlan()));
    }

    @Subscribe("threatField.entityLookup")
    public void onThreatFieldEntityLookup(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(Threat.class, this)
                .withContainer(unusedThreatsDc)
                .withOpenMode(DIALOG)
                .withSelectValidator(it -> it.getSelectedItems().size() == 1)
                .withSelectHandler(it -> {
                    var threat = it.iterator().next();
                    if (hasThreat(threat)) {
                        notifications.create()
                                .withType(WARNING)
                                .withCaption(messages.getMessage("projectAlreadyHaveThreat"))
                                .show();
                    } else {
                        actualThreatDc.getItem().setThreat(threat);
                    }
                })
                .show();
    }

    @Subscribe("scenariosTable.add")
    public void onScenariosTableAdd(Action.ActionPerformedEvent event) {
        var scenarioBrowse = screenBuilders.lookup(ThreatScenario.class, this)
                .withScreenClass(ThreatScenarioBrowse.class)
                .withOpenMode(DIALOG)
                .withSelectHandler(actualThreatDc.getItem().getScenarios()::addAll)
                .build();

        scenarioBrowse.getThreatScenariosDc().setItems(
                threatScenarioRepository.getByIdsExcluded(
                        scenariosDc.getItems().stream()
                                .map(DefaultEntity::getId)
                                .toList(),
                        actualThreatDc.getFetchPlan().getProperty("scenarios").getFetchPlan()));

        scenarioBrowse.show();
    }

    private boolean hasThreat(Threat it) {
        return project.getActualThreats().stream()
                .map(ActualThreat::getThreat)
                .anyMatch(isEqual(it));
    }
}