package ru.sstu.ifbs.screen.threat;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ru.sstu.ifbs.entity.Threat;

@UiController("gwf_Threat.browse")
@UiDescriptor("threat-browse.xml")
@LookupComponent("threatsTable")
public class ThreatBrowse extends StandardLookup<Threat> {
}