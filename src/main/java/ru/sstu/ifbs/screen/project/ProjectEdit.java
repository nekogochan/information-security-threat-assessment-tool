package ru.sstu.ifbs.screen.project;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Project;

@UiController("gwf_Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
}