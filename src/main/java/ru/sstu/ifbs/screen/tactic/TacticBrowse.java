package ru.sstu.ifbs.screen.tactic;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.tactic.Tactic;

@UiController("gwf_Tactic.browse")
@UiDescriptor("tactic-browse.xml")
@LookupComponent("tacticsTable")
public class TacticBrowse extends StandardLookup<Tactic> {
}