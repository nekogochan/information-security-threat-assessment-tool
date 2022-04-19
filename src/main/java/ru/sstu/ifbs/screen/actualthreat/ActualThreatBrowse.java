package ru.sstu.ifbs.screen.actualthreat;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ActualThreat;

@UiController("gwf_ActualThreat.browse")
@UiDescriptor("actual-threat-browse.xml")
@LookupComponent("actualThreatsTable")
public class ActualThreatBrowse extends StandardLookup<ActualThreat> {
}