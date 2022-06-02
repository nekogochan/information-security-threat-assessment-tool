package ru.sstu.ifbs.screen.threatimplmethod;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.ThreatImplMethod;

@UiController("gwf_ThreatImplMethod.edit")
@UiDescriptor("threat-impl-method-edit.xml")
@EditedEntityContainer("threatImplMethodDc")
public class ThreatImplMethodEdit extends StandardEditor<ThreatImplMethod> {
}