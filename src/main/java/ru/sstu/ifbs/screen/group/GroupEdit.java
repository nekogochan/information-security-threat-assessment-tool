package ru.sstu.ifbs.screen.group;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Group;

@UiController("gwf_Group.edit")
@UiDescriptor("group-edit.xml")
@EditedEntityContainer("groupDc")
public class GroupEdit extends StandardEditor<Group> {
}