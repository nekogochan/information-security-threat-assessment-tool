package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.core.EntityStates;
import io.jmix.core.Messages;
import io.jmix.ui.Fragments;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.*;
import io.jmix.ui.model.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.backoffice.collectors.ScrollBoxCollector;
import ru.sstu.ifbs.backoffice.collectors.VBoxCollector;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ScenarioTactic;
import ru.sstu.ifbs.entity.storage.tactic.Tactic;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;
import ru.sstu.ifbs.gui.Toggle;
import ru.sstu.ifbs.repository.ThreatScenarioRepository;
import ru.sstu.ifbs.screen.threatscenario.scenariotacticfrag.ScenarioTacticFrag;
import ru.sstu.ifbs.serivce.CopyService;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

import static io.jmix.ui.Notifications.NotificationType.WARNING;
import static io.jmix.ui.icon.JmixIcon.EYE;
import static io.jmix.ui.icon.JmixIcon.EYE_SLASH;
import static io.jmix.ui.screen.OpenMode.DIALOG;
import static io.jmix.ui.screen.StandardOutcome.CLOSE;
import static java.util.Comparator.comparing;
import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.isEqual;
import static java.util.stream.Collectors.collectingAndThen;

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
    @Autowired
    private ScrollBoxCollector scrollBoxCollector;
    @Autowired
    private CopyService copyService;
    @Autowired
    private ScreenBuilders screenBuilders;
    @Autowired
    private ThreatScenarioRepository threatScenarioRepository;
    @Autowired
    private Action toggleViewMode;
    @Autowired
    private TabSheet tabSheet;
    @Autowired
    private VBoxLayout viewModeSourcesBox;
    @Autowired
    private EntityStates entityStates;
    @Autowired
    private VBoxLayout viewModeSourcesOutBox;

    private Toggle viewModeToggle;

    public void init(ThreatScenario threatScenario, Consumer<ThreatScenario> onCommit) {
        threatScenarioDc.setItem(threatScenario);
        this.onCommit = onCommit;
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        tacticsDl.load();
        this.viewModeToggle = new Toggle() {
            @Override
            public void onToggleOn() {
                getTacticFragments().forEach(it -> it.getViewModeToggle().setOn(true));
                toggleViewMode.setIcon(EYE_SLASH.iconName());
                toggleViewMode.setCaption(messages.getMessage("actions.ViewMode.Off"));
                threatScenarioDc.getItem().getSources().forEach(it -> {
                    var label = uiComponents.create(Label.TYPE_STRING);
                    label.setValue(it.getName());
                    viewModeSourcesBox.add(label);
                });
                tabSheet.setSelectedTab("tacticsTab");
                tabSheet.setTabsVisible(false);
                viewModeSourcesOutBox.setVisible(true);
                addTacticBtn.setVisible(false);
            }

            @Override
            public void onToggleOff() {
                getTacticFragments().forEach(it -> it.getViewModeToggle().setOn(false));
                toggleViewMode.setIcon(EYE.iconName());
                toggleViewMode.setCaption(messages.getMessage("actions.ViewMode.On"));
                viewModeSourcesBox.removeAll();
                tabSheet.setTabsVisible(true);
                viewModeSourcesOutBox.setVisible(false);
                addTacticBtn.setVisible(true);
            }
        };
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        requireNonNull(onCommit, "onCommit is null, maybe forgot to call 'init' method");
        refreshUI();
        viewModeToggle.setOn(
                !entityStates.isNew(threatScenarioDc.getItem()));
    }

    @Subscribe("windowCommitAndClose")
    public void onWindowCommitAndClose(Action.ActionPerformedEvent event) {
        if (isEmpty(threatScenarioDc.getItem())) {
            notifications.create()
                    .withType(WARNING)
                    .withCaption(messages.getMessage("scenarioShouldntBeEmpty"))
                    .show();
        } else {
            onCommit.accept(threatScenarioDc.getItem());
            close(CLOSE);
        }
    }

    @Subscribe("windowClose")
    public void onWindowClose(Action.ActionPerformedEvent event) {
        close(CLOSE);
    }

    @Subscribe("copy")
    public void onCopy(Action.ActionPerformedEvent event) {
        screenBuilders.lookup(Threat.class, this)
                .withOpenMode(DIALOG)
                .withSelectValidator(this::singleSelected)
                .withSelectHandler(threats -> {
                    var threat = threats.iterator().next();
                    var scenarioLookup = screenBuilders.lookup(ThreatScenario.class, this)
                            .withScreenClass(ThreatScenarioBrowse.class)
                            .withOpenMode(DIALOG)
                            .withSelectValidator(this::singleSelected)
                            .withTransformation(scenarios -> threatScenarioRepository.reloadAll(
                                    scenarios, threatScenarioDc.getFetchPlan()))
                            .withSelectHandler(scenarios -> replace(scenarios.iterator().next()))
                            .build();
                    var lookupThreatScenariosDc = scenarioLookup.getThreatScenariosDc();
                    var lookupItems = threatScenarioRepository.getByThreat(threat, threatScenarioDc.getFetchPlan());
                    lookupThreatScenariosDc.setItems(lookupItems);
                    scenarioLookup.show();
                })
                .show();
    }

    @Subscribe("toggleViewMode")
    public void onToggleViewMode(Action.ActionPerformedEvent event) {
        this.viewModeToggle.fire();
    }

    private Stream<ScenarioTacticFrag> getTacticFragments() {
        return tacticsBox.getOwnComponentsStream()
                .map(Fragment.class::cast)
                .map(Fragment::getFrameOwner)
                .map(ScenarioTacticFrag.class::cast);
    }

    private void refreshUI() {
        refreshAddButtonPanel();
        refreshTacticsContainer();
    }

    private void refreshAddButtonPanel() {
        addTacticBtn.setPopupContent(
                tacticsDc.getItems().stream()
                        .filter(this::notInScenario)
                        .sorted()
                        .map(this::tacticToAddBtn)
                        .collect(collectingAndThen(
                                scrollBoxCollector.wrap(vBoxCollector),
                                it -> {
                                    it.setWidth("500px");
                                    return it;
                                }
                        )));
    }

    private void refreshTacticsContainer() {
        tacticsBox.removeAll();
        threatScenarioDc.getItem()
                .getTactics()
                .stream()
                .sorted()
                .map(this::toTacticFrag)
                .map(ScreenFragment::getFragment)
                .forEach(tacticsBox::add);
    }

    private Button tacticToAddBtn(Tactic tactic) {
        var button = uiComponents.create(LinkButton.class);
        button.setCaption("%s: %s".formatted(tactic.getCode(), tactic.getName()));
        button.addClickListener(ev -> {
            var scenarioTactic = dataContext.create(ScenarioTactic.class);
            scenarioTactic.setThreatScenario(threatScenarioDc.getItem());
            scenarioTactic.setValue(tactic);
            threatScenarioDc.getItem().getTactics().add(scenarioTactic);
            refreshUI();
        });
        button.setAlignment(Component.Alignment.MIDDLE_LEFT);
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

    private void replace(ThreatScenario newItem) {
        newItem = copyService.copy(newItem, dataContext::merge, dataContext::merge, dataContext::merge);
        var oldItem = threatScenarioDc.getItem();
        dataContext.remove(oldItem);
        oldItem.getTactics().forEach(tac -> {
            tac.getTechniques().forEach(dataContext::remove);
            dataContext.remove(tac);
        });
        threatScenarioDc.setItem(newItem);
        refreshUI();
    }

    private boolean singleSelected(LookupScreen.ValidationContext<?> it) {
        return it.getSelectedItems().size() == 1;
    }
}