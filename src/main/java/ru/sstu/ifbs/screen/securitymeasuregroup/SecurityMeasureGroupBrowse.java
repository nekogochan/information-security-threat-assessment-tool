package ru.sstu.ifbs.screen.securitymeasuregroup;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasureGroup;

@UiController("gwf_SecurityMeasureGroup.browse")
@UiDescriptor("security-measure-group-browse.xml")
@LookupComponent("securityMeasureGroupsTable")
public class SecurityMeasureGroupBrowse extends StandardLookup<SecurityMeasureGroup> {
}