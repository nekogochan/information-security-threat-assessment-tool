package ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag.scenariotechniquefrag;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.PopupView;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.ScenarioTechnique;

import static java.util.Objects.requireNonNull;

@UiController("gwf_ScenarioTechniqueFrag")
@UiDescriptor("scenario-technique-frag.xml")
public class ScenarioTechniqueFrag extends ScreenFragment {

    private Runnable onDelete;
    private ScenarioTechnique technique;

    @Autowired
    private Label<String> header;
    @Autowired
    private HBoxLayout mainInfoBox;

    public void init(ScenarioTechnique technique, Runnable onDelete) {
        requireNonNull(technique);
        requireNonNull(onDelete);
        this.technique = technique;
        this.onDelete = onDelete;
    }

    @Subscribe
    public void onAttach(AttachEvent event) {
        requireNonNull(onDelete, "onDelete is null, maybe forgot to call `init` method?");
        requireNonNull(technique, "technique is null, maybe forgot to call 'init' method?");
        var tech = technique.getValue();
        header.setValue(tech.getCode() + ": " + tech.getName());
        mainInfoBox.setContextHelpText(tech.getDescription());
    }

    @Subscribe("deleteBtn")
    public void onDeleteBtnClick(Button.ClickEvent event) {
        onDelete.run();
    }
}