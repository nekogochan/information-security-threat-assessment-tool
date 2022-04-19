package ru.sstu.ifbs.screen.threat;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Threat;

@UiController("gwf_Threat.edit")
@UiDescriptor("threat-edit.xml")
@EditedEntityContainer("threatDc")
public class ThreatEdit extends StandardEditor<Threat> {
}