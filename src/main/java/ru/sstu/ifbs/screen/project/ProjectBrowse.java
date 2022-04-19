package ru.sstu.ifbs.screen.project;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Project;

@UiController("gwf_Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
}