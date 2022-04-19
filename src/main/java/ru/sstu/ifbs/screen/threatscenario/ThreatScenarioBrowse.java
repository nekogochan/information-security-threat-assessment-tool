package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ThreatScenario;

@UiController("gwf_ThreatScenario.browse")
@UiDescriptor("threat-scenario-browse.xml")
@LookupComponent("threatScenariosTable")
public class ThreatScenarioBrowse extends StandardLookup<ThreatScenario> {
}