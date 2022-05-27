package ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag;

import io.jmix.ui.Fragments;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.backoffice.collectors.ScrollBoxCollector;
import ru.sstu.ifbs.backoffice.collectors.VBoxCollector;
import ru.sstu.ifbs.entity.storage.scenario.ScenarioTactic;
import ru.sstu.ifbs.entity.storage.scenario.ScenarioTechnique;
import ru.sstu.ifbs.entity.storage.tactic.Tactic;
import ru.sstu.ifbs.entity.storage.tactic.Technique;
import ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag.scenariotechniquefrag.ScenarioTechniqueFrag;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.collectingAndThen;

@UiController("gwf_ScenarioTacticFrag")
@UiDescriptor("scenario-tactic-frag.xml")
public class ScenarioTacticFrag extends ScreenFragment {

    private Runnable onDelete;
    private DataContext dataContext;

    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private Fragments fragments;
    @Autowired
    private VBoxLayout techniquesBox;
    @Autowired
    private PopupButton addTechniqueBtn;
    @Autowired
    private VBoxCollector vBoxCollector;
    @Autowired
    private InstanceContainer<ScenarioTactic> tacticDc;
    @Autowired
    private Label<String> header;
    @Autowired
    private ScrollBoxCollector scrollBoxCollector;

    public void init(DataContext parentDataContext, ScenarioTactic tactic, Runnable onDelete) {
        requireNonNull(tactic);
        requireNonNull(onDelete);
        tacticDc.setItem(tactic);
        this.onDelete = onDelete;
        this.dataContext = parentDataContext;
    }

    @Subscribe
    public void onAttach(AttachEvent event) {
        requireNonNull(onDelete, "onDelete is null, maybe forgot to call `init` method?");
        var item = tacticDc.getItem().getValue();
        header.setValue("%s: %s".formatted(item.getCode(), item.getName()));
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
                tacticDc.getItem().getValue()
                        .getTechniques()
                        .stream()
                        .filter(this::notInTactic)
                        .sorted()
                        .map(this::techniqueToBtn)
                        .collect(scrollBoxCollector.wrap(vBoxCollector)));
    }

    private Button techniqueToBtn(Technique technique) {
        var button = uiComponents.create(Button.class);
        button.setCaption(technique.getCode());
        button.addClickListener(ev -> {
            var scenarioTechnique = dataContext.create(ScenarioTechnique.class);
            scenarioTechnique.setTactic(tacticDc.getItem());
            scenarioTechnique.setValue(technique);
            tacticDc.getItem().getTechniques().add(scenarioTechnique);
            refreshUI();
        });
        button.setWidthFull();
        return button;
    }

    private void refreshTechniquesContainer() {
        techniquesBox.removeAll();
        tacticDc.getItem().getTechniques()
                .stream()
                .sorted()
                .map(this::toTechniqueFrag)
                .map(ScreenFragment::getFragment)
                .forEach(techniquesBox::add);
    }

    private ScenarioTechniqueFrag toTechniqueFrag(ScenarioTechnique technique) {
        var frag = fragments.create(this, ScenarioTechniqueFrag.class);
        frag.init(technique, () -> {
            tacticDc.getItem().getTechniques().remove(technique);
            dataContext.remove(technique);
            refreshUI();
        });
        return frag;
    }

    private boolean notInTactic(Technique technique) {
        return tacticDc.getItem().getTechniques()
                .stream()
                .map(ScenarioTechnique::getValue)
                .noneMatch(isEqual(technique));
    }
}