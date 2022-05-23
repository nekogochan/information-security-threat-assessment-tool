package ru.sstu.ifbs.screen.technique;

import io.jmix.ui.action.Action;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.Validatable;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.Technique;

import java.util.List;
import java.util.function.Consumer;

import static io.jmix.ui.screen.StandardOutcome.CLOSE;

@UiController("gwf_Technique.edit")
@UiDescriptor("technique-edit.xml")
public class TechniqueEdit extends Screen {

    @Autowired
    private TextField<String> codeField;
    @Autowired
    private TextField<String> nameField;
    @Autowired
    private TextArea<String> descriptionField;
    @Autowired
    private InstanceContainer<Technique> techniqueDc;

    private Consumer<Technique> onCommit;

    public void init(Technique technique, Consumer<Technique> onCommit) {
        techniqueDc.setItem(technique);
        this.onCommit = onCommit;
    }

    @Subscribe("windowCommitAndClose")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        List.of(codeField, nameField, descriptionField).forEach(Validatable::validate);
        onCommit.accept(techniqueDc.getItem());
        close(CLOSE);
    }

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        close(CLOSE);
    }
}