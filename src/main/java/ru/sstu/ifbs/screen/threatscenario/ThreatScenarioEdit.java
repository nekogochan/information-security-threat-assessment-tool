package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ThreatScenario;

@UiController("gwf_ThreatScenario.edit")
@UiDescriptor("threat-scenario-edit.xml")
@EditedEntityContainer("threatScenarioDc")
public class ThreatScenarioEdit extends StandardEditor<ThreatScenario> {
}