package ru.sstu.ifbs.screen.threatscenario.tacticfrag;

import io.jmix.ui.Fragments;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Label;
import io.jmix.ui.component.PopupView;
import io.jmix.ui.component.VBoxLayout;
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
import ru.sstu.ifbs.screen.threatscenario.tacticfrag.techniquefrag.TechniqueFrag;

import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;

@UiController("gwf_TacticFrag")
@UiDescriptor("tactic-frag.xml")
public class TacticFrag extends ScreenFragment {

    private Runnable onDelete;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private Label<String> description;
    @Autowired
    private Label<String> header;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private PopupView addScenarioBtn;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private InstanceContainer<ScenarioTactic> scenarioTacticDc;
    @Autowired
    private CollectionContainer<Tactic> tacticsDc;
    @Autowired
    private VBoxLayout techniquesBox;
    @Autowired
    private Fragments fragments;

    public void init(ScenarioTactic tactic, Runnable onDelete) {
        requireNonNull(tactic);
        requireNonNull(onDelete);
        scenarioTacticDc.setItem(tactic);
        this.onDelete = onDelete;
    }

    @Subscribe
    public void onAttach(AttachEvent event) {
        requireNonNull(onDelete, "onDelete is null, maybe forgot to call `init` method?");
        var tactic = scenarioTacticDc.getItem().getValue();
        header.setValue(tactic.getCode() + ": " + tactic.getName());
        description.setValue(tactic.getDescription());
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
        addScenarioBtn.setPopupContent(
                scenarioTacticDc.getItem()
                        .getValue()
                        .getTechniques()
                        .stream()
                        .filter(this::notInTactic)
                        .sorted(comparing(Technique::getCode, String.CASE_INSENSITIVE_ORDER))
                        .map(this::techniqueToBtn)
                        .collect(applicationContext.getBean(VBoxCollector.class))
        );
    }

    private Button techniqueToBtn(Technique technique) {
        var button = uiComponents.create(Button.class);
        button.setCaption(technique.getCode());
        button.addClickListener(ev -> {
            var scenarioTechnique = dataContext.create(ScenarioTechnique.class);
            scenarioTechnique.setTactic(scenarioTacticDc.getItem());
            scenarioTechnique.setValue(technique);
            scenarioTacticDc.getItem().getTechniques().add(scenarioTechnique);
            refreshUI();
        });
        return button;
    }

    private void refreshTechniquesContainer() {
        techniquesBox.removeAll();
        scenarioTacticDc.getItem()
                .getTechniques()
                .stream()
                .sorted(comparing(it -> it.getValue().getCode(), String.CASE_INSENSITIVE_ORDER))
                .map(this::toTechniqueFrag)
                .map(ScreenFragment::getFragment)
                .forEach(techniquesBox::add);
    }

    private TechniqueFrag toTechniqueFrag(ScenarioTechnique technique) {
        var frag = fragments.create(this, TechniqueFrag.class);
        frag.init(technique, () -> {
            scenarioTacticDc.getItem().getTechniques().remove(technique);
            refreshUI();
        });
        return frag;
    }

    private boolean notInTactic(Technique technique) {
        return scenarioTacticDc.getItem()
                .getTechniques()
                .stream()
                .map(ScenarioTechnique::getValue)
                .noneMatch(isEqual(technique));
    }
}