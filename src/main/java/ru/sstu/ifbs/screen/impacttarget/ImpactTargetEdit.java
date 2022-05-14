package ru.sstu.ifbs.screen.impacttarget;

import io.jmix.ui.screen.EditedEntityContainer;
import io.jmix.ui.screen.StandardEditor;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ru.sstu.ifbs.entity.ImpactTarget;

@UiController("gwf_ImpactTarget.edit")
@UiDescriptor("impact-target-edit.xml")
@EditedEntityContainer("impactTargetDc")
public class ImpactTargetEdit extends StandardEditor<ImpactTarget> {
}