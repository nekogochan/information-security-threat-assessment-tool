package ru.sstu.ifbs.screen.threatimplmethod;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.ThreatImplMethod;

@UiController("gwf_ThreatImplMethod.browse")
@UiDescriptor("threat-impl-method-browse.xml")
@LookupComponent("threatImplMethodsTable")
public class ThreatImplMethodBrowse extends StandardLookup<ThreatImplMethod> {
}