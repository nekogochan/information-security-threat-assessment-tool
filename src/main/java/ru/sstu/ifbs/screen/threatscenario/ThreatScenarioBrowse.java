package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ru.sstu.ifbs.entity.ThreatScenario;

@UiController("gwf_ThreatScenario.browse")
@UiDescriptor("threat-scenario-browse.xml")
@LookupComponent("threatScenariosTable")
public class ThreatScenarioBrowse extends StandardLookup<ThreatScenario> {
}