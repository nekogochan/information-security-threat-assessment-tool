package ru.sstu.ifbs.screen.project;

import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.Project;
import ru.sstu.ifbs.screen.actualthreat.ActualThreatEdit;

@UiController("gwf_Project.edit")
@UiDescriptor("project-edit.xml")
@EditedEntityContainer("projectDc")
public class ProjectEdit extends StandardEditor<Project> {
    @Autowired
    private InstanceContainer<Project> projectDc;

    @Install(to = "actualThreatsTable.create", subject = "screenConfigurer")
    private void actualThreatsTableCreateScreenConfigurer(ActualThreatEdit screen) {
        screen.init(projectDc.getItem());
    }

    @Install(to = "actualThreatsTable.edit", subject = "screenConfigurer")
    private void actualThreatsTableEditScreenConfigurer(ActualThreatEdit screen) {
        screen.init(projectDc.getItem());
    }
}