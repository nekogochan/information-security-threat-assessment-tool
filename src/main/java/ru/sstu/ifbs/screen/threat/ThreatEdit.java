package ru.sstu.ifbs.screen.threat;

import io.jmix.core.Metadata;
import io.jmix.ui.Screens;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.screen.threatscenario.ThreatScenarioEdit;

import java.util.function.Consumer;

@UiController("gwf_Threat.edit")
@UiDescriptor("threat-edit.xml")
@EditedEntityContainer("threatDc")
public class ThreatEdit extends StandardEditor<Threat> {
    @Autowired
    private Screens screens;
    @Autowired
    private InstanceContainer<Threat> threatDc;
    @Autowired
    private CollectionPropertyContainer<ThreatScenario> threatScenariosDc;
    @Autowired
    private Table<ThreatScenario> scenariosTable;
    @Autowired
    private Metadata metadata;
    @Autowired
    private DataContext dataContext;

    @Subscribe("scenariosTable.create")
    public void onScenariosTableCreate(Action.ActionPerformedEvent event) {
        openThreatScenarioEdit(metadata.create(ThreatScenario.class),
                               it -> {
                                   it.setThreat(threatDc.getItem());
                                   threatScenariosDc.getMutableItems().add(it);
                               });
    }

    @Subscribe("scenariosTable.edit")
    public void onScenariosTableEdit(Action.ActionPerformedEvent event) {
        openThreatScenarioEdit(scenariosTable.getSingleSelected(), it -> scenariosTable.repaint());
    }

    private void openThreatScenarioEdit(ThreatScenario scenario, Consumer<ThreatScenario> onCommit) {
        var screen = screens.create(ThreatScenarioEdit.class);
        screen.init(scenario, it -> onCommit.accept((dataContext.merge(it))));
        screen.show();
    }
}