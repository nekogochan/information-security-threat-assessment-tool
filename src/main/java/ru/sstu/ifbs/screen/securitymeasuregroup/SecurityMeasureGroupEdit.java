package ru.sstu.ifbs.screen.securitymeasuregroup;

import io.jmix.core.Metadata;
import io.jmix.ui.Screens;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasureGroup;
import ru.sstu.ifbs.entity.storage.tactic.Technique;
import ru.sstu.ifbs.screen.securitymeasure.SecurityMeasureEdit;
import ru.sstu.ifbs.screen.technique.TechniqueEdit;

import java.util.function.Consumer;

import static io.jmix.ui.screen.OpenMode.DIALOG;

@UiController("gwf_SecurityMeasureGroup.edit")
@UiDescriptor("security-measure-group-edit.xml")
@EditedEntityContainer("securityMeasureGroupDc")
public class SecurityMeasureGroupEdit extends StandardEditor<SecurityMeasureGroup> {

    @Autowired
    private Screens screens;
    @Autowired
    private Metadata metadata;
    @Autowired
    private InstanceContainer<SecurityMeasureGroup> securityMeasureGroupDc;
    @Autowired
    private CollectionPropertyContainer<SecurityMeasure> measuresDc;
    @Autowired
    private Table<SecurityMeasure> measuresTable;

    @Subscribe("measuresTable.create")
    public void onMeasuresTableCreate(Action.ActionPerformedEvent event) {
        openMeasureEditScreen(metadata.create(SecurityMeasure.class),
                                it -> {
                                    it.setSecurityMeasureGroup(securityMeasureGroupDc.getItem());
                                    measuresDc.getMutableItems().add(it);
                                });
    }

    @Subscribe("measuresTable.edit")
    public void onMeasuresTableEdit(Action.ActionPerformedEvent event) {
        openMeasureEditScreen(measuresTable.getSingleSelected(), it -> measuresTable.repaint());
    }

    private void openMeasureEditScreen(SecurityMeasure measure, Consumer<SecurityMeasure> onCommit) {
        var screen = screens.create(SecurityMeasureEdit.class, DIALOG);
        screen.init(measure, onCommit);
        screen.show();
    }
}