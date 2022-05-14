package ru.sstu.ifbs.screen.threatscenario.tacticfrag.techniquefrag;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.ScenarioTechnique;

import static java.util.Objects.requireNonNull;

@UiController("gwf_TechniqueFrag")
@UiDescriptor("technique-frag.xml")
public class TechniqueFrag extends ScreenFragment {

    private Runnable onDelete;
    @Autowired
    private InstanceContainer<ScenarioTechnique> scenarioTechniqueDc;
    @Autowired
    private Label<String> header;
    @Autowired
    private Label description;

    public void init(ScenarioTechnique technique, Runnable onDelete) {
        requireNonNull(technique);
        requireNonNull(onDelete);
        scenarioTechniqueDc.setItem(technique);
        this.onDelete = onDelete;
    }

    @Subscribe
    public void onAttach(AttachEvent event) {
        requireNonNull(onDelete, "onDelete is null, maybe forgot to call `init` method?");
        var tech = scenarioTechniqueDc.getItem().getValue();
        header.setValue(tech.getCode() + ": " + tech.getName());
        description.setValue(tech.getDescription());
    }

    @Subscribe("deleteBtn")
    public void onDeleteBtnClick(Button.ClickEvent event) {
        onDelete.run();
    }
}