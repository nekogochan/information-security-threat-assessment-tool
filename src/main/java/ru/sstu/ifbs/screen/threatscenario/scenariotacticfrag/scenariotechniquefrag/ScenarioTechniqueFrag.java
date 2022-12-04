package ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag.scenariotechniquefrag;

import io.jmix.ui.component.Button;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.PopupView;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.storage.scenario.ScenarioTechnique;
import ru.sstu.ifbs.entity.storage.tactic.Technique;
import ru.sstu.ifbs.gui.Toggle;

import static java.util.Objects.requireNonNull;

@UiController("gwf_ScenarioTechniqueFrag")
@UiDescriptor("scenario-technique-frag.xml")
public class ScenarioTechniqueFrag extends ScreenFragment {

    private Runnable onDelete;

    @Autowired
    private PopupView descriptionPopup;
    @Autowired
    private InstanceContainer<ScenarioTechnique> techniqueDc;
    @Autowired
    private Label<String> header;

    private Toggle viewModeToggle = new Toggle() {
        @Override
        public void onToggleOn() {
            descriptionPopup.setVisible(false);
            getFragment().setWidth("165px");
        }

        @Override
        public void onToggleOff() {
            descriptionPopup.setVisible(true);
            getFragment().setWidth("200px");
        }
    };

    public Toggle getViewModeToggle() {
        return viewModeToggle;
    }

    public void init(ScenarioTechnique technique, Runnable onDelete) {
        requireNonNull(technique);
        requireNonNull(onDelete);
        techniqueDc.setItem(technique);
        this.onDelete = onDelete;
    }

    @Subscribe
    public void onAttach(AttachEvent event) {
        requireNonNull(onDelete, "onDelete is null, maybe forgot to call `init` method?");
        var item = techniqueDc.getItem().getValue();
        header.setValue("%s: %s".formatted(item.getCode(), item.getName()));
    }

    @Subscribe("deleteBtn")
    public void onDeleteBtnClick(Button.ClickEvent event) {
        onDelete.run();
    }
}