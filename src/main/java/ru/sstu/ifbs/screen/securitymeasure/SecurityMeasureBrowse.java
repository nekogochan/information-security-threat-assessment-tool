package ru.sstu.ifbs.screen.securitymeasure;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;

@UiController("gwf_SecurityMeasures.browse")
@UiDescriptor("security-measure-browse.xml")
@LookupComponent("securityMeasuresTable")
public class SecurityMeasureBrowse extends StandardLookup<SecurityMeasure> {
}