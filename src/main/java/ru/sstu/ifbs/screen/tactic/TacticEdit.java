package ru.sstu.ifbs.screen.tactic;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Tactic;

@UiController("gwf_Tactic.edit")
@UiDescriptor("tactic-edit.xml")
@EditedEntityContainer("tacticDc")
public class TacticEdit extends StandardEditor<Tactic> {
}