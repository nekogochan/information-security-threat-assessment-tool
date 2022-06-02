package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

@UiController("gwf_ThreatScenario.browse")
@UiDescriptor("threat-scenario-browse.xml")
@LookupComponent("threatScenariosTable")
public class ThreatScenarioBrowse extends StandardLookup<ThreatScenario> {

    @Autowired
    private CollectionContainer<ThreatScenario> threatScenariosDc;

    public CollectionContainer<ThreatScenario> getThreatScenariosDc() {
        return threatScenariosDc;
    }
}