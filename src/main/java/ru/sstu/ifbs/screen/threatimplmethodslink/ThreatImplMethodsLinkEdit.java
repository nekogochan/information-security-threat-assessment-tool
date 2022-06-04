package ru.sstu.ifbs.screen.threatimplmethodslink;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.ThreatImplMethodsLink;

@UiController("gwf_ThreatImplMethodsLink.edit")
@UiDescriptor("threat-impl-methods-link-edit.xml")
@EditedEntityContainer("threatImplMethodDc")
public class ThreatImplMethodsLinkEdit extends StandardEditor<ThreatImplMethodsLink> {
}