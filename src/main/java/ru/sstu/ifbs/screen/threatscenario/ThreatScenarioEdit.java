package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.core.Messages;
import io.jmix.core.Metadata;
import io.jmix.ui.Fragments;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.HBoxLayout;
import io.jmix.ui.component.PopupButton;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.backoffice.collectors.VBoxCollector;
import ru.sstu.ifbs.entity.ScenarioTactic;
import ru.sstu.ifbs.entity.Tactic;
import ru.sstu.ifbs.entity.ThreatScenario;
import ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag.ScenarioTacticFrag;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import static io.jmix.ui.Notifications.NotificationType.WARNING;
import static io.jmix.ui.component.Component.Alignment.MIDDLE_LEFT;
import static io.jmix.ui.screen.StandardOutcome.CLOSE;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;

@UiController("gwf_ThreatScenario.edit")
@UiDescriptor("threat-scenario-edit.xml")
public class ThreatScenarioEdit extends Screen {

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
    private DataContext dataContext;

    private Consumer<ThreatScenario> onCommit;
    @Autowired
    private Notifications notifications;
    @Autowired
    private Messages messages;
    @Autowired
    private CollectionLoader<Tactic> tacticsDl;
    @Autowired
    private VBoxCollector vBoxCollector;

    public void init(ThreatScenario threatScenario, Consumer<ThreatScenario> onCommit) {
        threatScenarioDc.setItem(threatScenario);
        this.onCommit = onCommit;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        tacticsDl.load();
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        requireNonNull(onCommit, "onCommit is null, maybe forgot to call 'init' method");
        refreshUI();
    }

    @Subscribe("windowCommitAndClose")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        if (isEmpty(threatScenarioDc.getItem())) {
            notifications.create()
                    .withType(WARNING)
                    .withCaption(messages.getMessage("scenarioShouldntBeEmpty"))
                    .show();
        } else {
            var tactics = threatScenarioDc.getItem().getTactics();
            tactics.stream().map(ScenarioTactic::getTechniques)
                    .forEach(it -> it.replaceAll(dataContext::merge));
            tactics.replaceAll(dataContext::merge);
            onCommit.accept(threatScenarioDc.getItem());
            close(CLOSE);
        }
    }

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        close(CLOSE);
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
                        .collect(vBoxCollector));
    }

    private void refreshTacticsContainer() {
        tacticsBox.removeAll();
        threatScenarioDc.getItem()
                .getTactics()
                .stream()
                .sorted(comparing(it -> it.getValue().getCode(), String.CASE_INSENSITIVE_ORDER))
                .map(this::toTacticFrag)
                .map(ScreenFragment::getFragment)
                .forEach(tacticsBox::add);
    }

    private Button tacticToAddBtn(Tactic tactic) {
        var button = uiComponents.create(Button.class);
        button.setCaption("%s: %s".formatted(tactic.getCode(), tactic.getName()));
        button.addClickListener(ev -> {
            var scenarioTactic = dataContext.create(ScenarioTactic.class);
            scenarioTactic.setThreatScenario(threatScenarioDc.getItem());
            scenarioTactic.setValue(tactic);
            threatScenarioDc.getItem().getTactics().add(scenarioTactic);
            refreshUI();
        });
        button.setWidthFull();
        return button;
    }

    private ScenarioTacticFrag toTacticFrag(ScenarioTactic tactic) {
        var frag = fragments.create(this, ScenarioTacticFrag.class);
        frag.init(dataContext, tactic, () -> {
            threatScenarioDc.getItem().getTactics().remove(tactic);
            dataContext.remove(tactic);
            refreshUI();
        });
        return frag;
    }

    private boolean notInScenario(Tactic tactic) {
        return threatScenarioDc.getItem().getTactics().stream()
                .map(ScenarioTactic::getValue)
                .noneMatch(isEqual(tactic));
    }

    private boolean isEmpty(ThreatScenario scenario) {
        var tactics = scenario.getTactics();
        return tactics.isEmpty() ||
                tactics.stream()
                        .map(ScenarioTactic::getTechniques)
                        .allMatch(List::isEmpty);
    }
}