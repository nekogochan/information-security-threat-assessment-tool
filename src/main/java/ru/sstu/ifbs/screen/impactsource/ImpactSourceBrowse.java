package ru.sstu.ifbs.screen.impactsource;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ImpactSource;

@UiController("gwf_ImpactSource.browse")
@UiDescriptor("impact-source-browse.xml")
@LookupComponent("impactSourcesTable")
public class ImpactSourceBrowse extends StandardLookup<ImpactSource> {
}