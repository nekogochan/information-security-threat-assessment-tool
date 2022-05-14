package ru.sstu.ifbs.screen.impacttarget;

import io.jmix.ui.screen.LookupComponent;
import io.jmix.ui.screen.StandardLookup;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import ru.sstu.ifbs.entity.ImpactTarget;

@UiController("gwf_ImpactTarget.browse")
@UiDescriptor("impact-target-browse.xml")
@LookupComponent("impactTargetsTable")
public class ImpactTargetBrowse extends StandardLookup<ImpactTarget> {
}