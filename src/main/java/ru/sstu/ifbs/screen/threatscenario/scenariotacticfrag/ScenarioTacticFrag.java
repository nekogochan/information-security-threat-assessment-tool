package ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag;

import io.jmix.core.Metadata;
import io.jmix.ui.Fragments;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.ScreenFragment;
import io.jmix.ui.screen.Subscribe;
import io.jmix.ui.screen.UiController;
import io.jmix.ui.screen.UiDescriptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.sstu.ifbs.backoffice.collectors.VBoxCollector;
import ru.sstu.ifbs.entity.ScenarioTactic;
import ru.sstu.ifbs.entity.ScenarioTechnique;
import ru.sstu.ifbs.entity.Tactic;
import ru.sstu.ifbs.entity.Technique;
import ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag.scenariotechniquefrag.ScenarioTechniqueFrag;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;

@UiController("gwf_ScenarioTacticFrag")
@UiDescriptor("scenario-tactic-frag.xml")
public class ScenarioTacticFrag extends ScreenFragment {

    private Runnable onDelete;
    private ScenarioTactic tactic;
    private DataContext dataContext;

    @Autowired
    private Label<String> header;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Fragments fragments;
    @Autowired
    private VBoxLayout techniquesBox;
    @Autowired
    private PopupButton addTechniqueBtn;
    @Autowired
    private HBoxLayout mainInfoBox;
    @Autowired
    private VBoxCollector vBoxCollector;

    public void init(DataContext parentDataContext, ScenarioTactic tactic, Runnable onDelete) {
        requireNonNull(tactic);
        requireNonNull(onDelete);
        this.tactic = tactic;
        this.onDelete = onDelete;
        this.dataContext = parentDataContext;
    }

    @Subscribe
    public void onAttach(AttachEvent event) {
        requireNonNull(onDelete, "onDelete is null, maybe forgot to call `init` method?");
        requireNonNull(tactic, "tactic is null, maybe forgot to call `init` method?");
        var tactic = this.tactic.getValue();
        header.setValue(tactic.getCode() + ": " + tactic.getName());
        mainInfoBox.setContextHelpText(tactic.getDescription());
        refreshUI();
    }

    @Subscribe("deleteBtn")
    public void onDeleteBtnClick(Button.ClickEvent event) {
        onDelete.run();
    }

    private void refreshUI() {
        refreshAddButtonPanel();
        refreshTechniquesContainer();
    }

    private void refreshAddButtonPanel() {
        addTechniqueBtn.setPopupContent(
                tactic.getValue()
                        .getTechniques()
                        .stream()
                        .filter(this::notInTactic)
                        .sorted(comparing(Technique::getCode, String.CASE_INSENSITIVE_ORDER))
                        .map(this::techniqueToBtn)
                        .collect(vBoxCollector)
        );
    }

    private Button techniqueToBtn(Technique technique) {
        var button = uiComponents.create(Button.class);
        button.setCaption(technique.getCode());
        button.addClickListener(ev -> {
            var scenarioTechnique = dataContext.create(ScenarioTechnique.class);
            scenarioTechnique.setTactic(tactic);
            scenarioTechnique.setValue(technique);
            tactic.getTechniques().add(scenarioTechnique);
            refreshUI();
        });
        button.setWidthFull();
        return button;
    }

    private void refreshTechniquesContainer() {
        techniquesBox.removeAll();
        tactic.getTechniques()
                .stream()
                .sorted(comparing(it -> it.getValue().getCode(), String.CASE_INSENSITIVE_ORDER))
                .map(this::toTechniqueFrag)
                .map(ScreenFragment::getFragment)
                .forEach(techniquesBox::add);
    }

    private ScenarioTechniqueFrag toTechniqueFrag(ScenarioTechnique technique) {
        var frag = fragments.create(this, ScenarioTechniqueFrag.class);
        frag.init(technique, () -> {
            tactic.getTechniques().remove(technique);
            dataContext.remove(technique);
            refreshUI();
        });
        return frag;
    }

    private boolean notInTactic(Technique technique) {
        return tactic.getTechniques()
                .stream()
                .map(ScenarioTechnique::getValue)
                .noneMatch(isEqual(technique));
    }
}