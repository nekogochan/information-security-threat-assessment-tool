package ru.sstu.ifbs.screen.group;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.project.Group;

@UiController("gwf_Group.browse")
@UiDescriptor("group-browse.xml")
@LookupComponent("groupsTable")
public class GroupBrowse extends StandardLookup<Group> {
}