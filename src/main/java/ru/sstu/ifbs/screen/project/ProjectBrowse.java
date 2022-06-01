package ru.sstu.ifbs.screen.project;

import io.jmix.core.MessageTools;
import io.jmix.core.Metadata;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.project.Project;

@UiController("gwf_Project.browse")
@UiDescriptor("project-browse.xml")
@LookupComponent("projectsTable")
public class ProjectBrowse extends StandardLookup<Project> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private MessageTools messageTools;
    @Autowired
    private Metadata metadata;

    @Install(to = "projectsTable.securityInfo", subject = "columnGenerator")
    private Component projectsTableSecurityInfoColumnGenerator(Project project) {
        var label = uiComponents.create(Label.TYPE_STRING);
        label.setValue(messageTools.getEntityCaption(metadata.getClass(project.getSecurityInfo())));
        return label;
    }
}