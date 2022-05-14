package ru.sstu.ifbs.screen.actualthreat;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ru.sstu.ifbs.entity.ActualThreat;

@UiController("gwf_ActualThreat.browse")
@UiDescriptor("actual-threat-browse.xml")
@LookupComponent("actualThreatsTable")
public class ActualThreatBrowse extends StandardLookup<ActualThreat> {
}