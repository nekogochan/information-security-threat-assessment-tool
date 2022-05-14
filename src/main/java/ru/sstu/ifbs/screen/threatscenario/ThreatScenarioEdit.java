package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.ui.Fragments;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.PopupButton;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import ru.sstu.ifbs.backoffice.collectors.HBoxCollector;
import ru.sstu.ifbs.entity.ScenarioTactic;
import ru.sstu.ifbs.entity.Tactic;
import ru.sstu.ifbs.entity.ThreatScenario;
import ru.sstu.ifbs.screen.threatscenario.tacticfrag.TacticFrag;

import static java.util.Comparator.comparing;
import static java.util.function.Predicate.isEqual;

@UiController("gwf_ThreatScenario.edit")
@UiDescriptor("threat-scenario-edit.xml")
@EditedEntityContainer("threatScenarioDc")
public class ThreatScenarioEdit extends StandardEditor<ThreatScenario> {

    @Autowired
    private CollectionContainer<Tactic> tacticsDc;
    @Autowired
    private PopupButton addTacticBtn;
    @Autowired
    private HBoxLayout tacticsBox;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private InstanceContainer<ThreatScenario> threatScenarioDc;
    @Autowired
    private Fragments fragments;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DataContext dataContext;

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        refreshUI();
    }

    private void refreshUI() {
        refreshAddButtonPanel();
        refreshTacticsContainer();
    }

    private void refreshAddButtonPanel() {
        addTacticBtn.setPopupContent(
                tacticsDc.getItems().stream()
                        .filter(this::notInScenario)
                        .sorted(comparing(Tactic::getCode, String.CASE_INSENSITIVE_ORDER))
                        .map(this::tacticToAddBtn)
                        .collect(applicationContext.getBean(HBoxCollector.class)));
    }

    private void refreshTacticsContainer() {
        tacticsBox.removeAll();
        threatScenarioDc.getItem()
                .getTactics()
                .stream()
                .sorted(comparing(it -> it.getValue().getCode(), String.CASE_INSENSITIVE_ORDER::compare))
                .map(this::toTacticFrag)
                .map(ScreenFragment::getFragment)
                .forEach(tacticsBox::add);
    }

    private Button tacticToAddBtn(Tactic tactic) {
        var button = uiComponents.create(Button.class);
        button.setCaption(tactic.getCode());
        button.addClickListener(ev -> {
            var scenarioTactic = dataContext.create(ScenarioTactic.class);
            scenarioTactic.setThreatScenario(threatScenarioDc.getItem());
            scenarioTactic.setValue(tactic);
            threatScenarioDc.getItem().getTactics().add(scenarioTactic);
            refreshUI();
        });
        return button;
    }

    private TacticFrag toTacticFrag(ScenarioTactic tactic) {
        var frag = fragments.create(this, TacticFrag.class);
        frag.init(tactic, () -> {
            threatScenarioDc.getItem().getTactics().remove(tactic);
            refreshUI();
        });
        return frag;
    }

    private boolean notInScenario(Tactic tactic) {
        return threatScenarioDc.getItem().getTactics().stream()
                .map(ScenarioTactic::getValue)
                .noneMatch(isEqual(tactic));
    }
}