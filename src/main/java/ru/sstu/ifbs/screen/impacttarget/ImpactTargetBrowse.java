package ru.sstu.ifbs.screen.impacttarget;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.ImpactTarget;

@UiController("gwf_ImpactTarget.browse")
@UiDescriptor("impact-target-browse.xml")
@LookupComponent("impactTargetsTable")
public class ImpactTargetBrowse extends StandardLookup<ImpactTarget> {
}