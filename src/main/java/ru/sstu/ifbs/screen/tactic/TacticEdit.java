package ru.sstu.ifbs.screen.tactic;

import io.jmix.core.Metadata;
import io.jmix.ui.Screens;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Table;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.tactic.Tactic;
import ru.sstu.ifbs.entity.storage.tactic.Technique;
import ru.sstu.ifbs.screen.technique.TechniqueEdit;

import java.util.function.Consumer;

import static io.jmix.ui.screen.OpenMode.DIALOG;

@UiController("gwf_Tactic.edit")
@UiDescriptor("tactic-edit.xml")
@EditedEntityContainer("tacticDc")
public class TacticEdit extends StandardEditor<Tactic> {

    @Autowired
    private Table<Technique> techniquesTable;
    @Autowired
    private InstanceContainer<Tactic> tacticDc;
    @Autowired
    private CollectionPropertyContainer<Technique> techniquesDc;
    @Autowired
    private Screens screens;
    @Autowired
    private Metadata metadata;

    @Subscribe("techniquesTable.create")
    public void onTechniquesTableCreate(Action.ActionPerformedEvent event) {
        openTechniqueEditScreen(metadata.create(Technique.class),
                                it -> {
                                    it.setTactic(tacticDc.getItem());
                                    techniquesDc.getMutableItems().add(it);
                                });
    }

    @Subscribe("techniquesTable.edit")
    public void onTechniquesTableEdit(Action.ActionPerformedEvent event) {
        openTechniqueEditScreen(techniquesTable.getSingleSelected(), it -> techniquesTable.repaint());
    }

    private void openTechniqueEditScreen(Technique technique, Consumer<Technique> onCommit) {
        var screen = screens.create(TechniqueEdit.class, DIALOG);
        screen.init(technique, onCommit);
        screen.show();
    }
}