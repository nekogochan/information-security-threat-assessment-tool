package ru.sstu.ifbs.screen.actualthreat;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ru.sstu.ifbs.entity.ActualThreat;

@UiController("gwf_ActualThreat.edit")
@UiDescriptor("actual-threat-edit.xml")
@EditedEntityContainer("actualThreatDc")
public class ActualThreatEdit extends StandardEditor<ActualThreat> {
}