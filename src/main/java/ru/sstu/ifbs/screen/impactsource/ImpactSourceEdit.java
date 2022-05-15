package ru.sstu.ifbs.screen.impactsource;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ImpactSource;

@UiController("gwf_ImpactSource.edit")
@UiDescriptor("impact-source-edit.xml")
@EditedEntityContainer("impactSourceDc")
public class ImpactSourceEdit extends StandardEditor<ImpactSource> {
}