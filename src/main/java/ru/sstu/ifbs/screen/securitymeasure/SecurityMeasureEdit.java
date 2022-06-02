package ru.sstu.ifbs.screen.securitymeasure;

import io.jmix.ui.action.Action;
import io.jmix.ui.component.Form;
import io.jmix.ui.component.TextArea;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.Validatable;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;
import ru.sstu.ifbs.entity.storage.tactic.Technique;

import java.util.List;
import java.util.function.Consumer;

import static io.jmix.ui.screen.StandardOutcome.CLOSE;

@UiController("gwf_SecurityMeasures.edit")
@UiDescriptor("security-measure-edit.xml")
@EditedEntityContainer("securityMeasureDc")
public class SecurityMeasureEdit extends Screen {

    @Autowired
    private Form form;
    @Autowired
    private InstanceContainer<SecurityMeasure> securityMeasureDc;

    private Consumer<SecurityMeasure> onCommit;

    public void init(SecurityMeasure technique, Consumer<SecurityMeasure> onCommit) {
        securityMeasureDc.setItem(technique);
        this.onCommit = onCommit;
    }

    @Subscribe("windowCommitAndClose")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        form.getComponents(0).stream()
                .filter(Validatable.class::isInstance)
                .map(Validatable.class::cast)
                .forEach(Validatable::validate);
        onCommit.accept(securityMeasureDc.getItem());
        close(CLOSE);
    }

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        close(CLOSE);
    }

}