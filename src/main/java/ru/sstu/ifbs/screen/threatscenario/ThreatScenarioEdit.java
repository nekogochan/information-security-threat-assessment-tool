package ru.sstu.ifbs.screen.threatscenario;

import io.jmix.core.Messages;
import io.jmix.ui.UiComponents;
import io.jmix.ui.component.*;
import io.jmix.ui.icon.JmixIcon;
import io.jmix.ui.model.CollectionPropertyContainer;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.*;

import java.util.Comparator;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.function.Predicate.isEqual;

@UiController("gwf_ThreatScenario.edit")
@UiDescriptor("threat-scenario-edit.xml")
@EditedEntityContainer("threatScenarioDc")
public class ThreatScenarioEdit extends StandardEditor<ThreatScenario> {

    @Autowired
    private CollectionPropertyContainer<ScenarioTactic> tacticsDc;
    @Autowired
    private PopupButton addTacticBtn;
    @Autowired
    private HBoxLayout tacticsContainer;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private DataContext dataContext;
    @Autowired
    private InstanceContainer<ThreatScenario> threatScenarioDc;
    @Autowired
    private Messages messages;

    @Subscribe
    public void onInit(InitEvent event) {
        refresh();
    }

    void refresh() {
        refreshAddBtn();
        refreshTacticsContainer();
    }

    void refreshAddBtn() {
        addTacticBtn.setPopupContent(
                stream(Tactic.values())
                        .filter(this::notInDc)
                        .map(this::tacticToBtn)
                        .collect(
                                () -> {
                                    var box = uiComponents.create(VBoxLayout.class);
                                    box.setSpacing(true);
                                    return box;
                                },
                                VBoxLayout::add,
                                (major, minor) -> minor.getOwnComponents().forEach(major::add)
                        )
        );
    }

    void refreshTacticsContainer() {
        tacticsContainer.removeAll();
        tacticsDc.getItems().stream()
                .sorted(comparing(ScenarioTactic::getValue, comparingInt(Enum::ordinal)))
                .map(this::toTacticColumn)
                .forEach(tacticsContainer::add);
    }

    VBoxLayout toTacticColumn(ScenarioTactic tactic) {
        var box = uiComponents.create(VBoxLayout.class);
        box.add(toTacticHeader(tactic));
        box.setSpacing(true);
        tactic.getTechniques().stream()
                .map(tech -> toTechniqueBox(tactic, tech))
                .forEach(box::add);
        box.add(tacticToAddTechniquesBtn(tactic));
        return box;
    }

    HBoxLayout toTacticHeader(ScenarioTactic tactic) {
        var label = label(messages.getMessage("ru.sstu.ifbs.entity", "Tactic." + tactic.getValue().getId()));
        var descriptionLabel = label(messages.getMessage(
                "ru.sstu.ifbs.entity", "Tactic." + tactic.getId() + ".description"));

        var deleteBtn = btn(messages.getMessage("actions.Remove"), () -> {
            tacticsDc.getMutableItems().remove(tactic);
            refresh();
        });

        var popup = uiComponents.create(PopupButton.class);
        popup.setIcon(JmixIcon.ANGLE_DOWN.iconName());

        var popupContent = uiComponents.create(VBoxLayout.class);
        popupContent.add(deleteBtn);
        popupContent.add(descriptionLabel);
        popupContent.setSpacing(true);
        popup.setPopupContent(popupContent);

        var tacticHeader = uiComponents.create(HBoxLayout.class);
        tacticHeader.add(label);
        tacticHeader.add(popup);
        tacticHeader.setSpacing(true);

        return tacticHeader;
    }

    HBoxLayout toTechniqueBox(ScenarioTactic tactic, ScenarioTechnique technique) {
        var label = label(messages.getMessage("ru.sstu.ifbs.entity", "Technique." + technique.getValue().getId()));
        var descriptionLabel = label(messages.getMessage(
                "ru.sstu.ifbs.entity", "Technique." + technique.getId() + ".description"));

        var deleteBtn = btn(messages.getMessage("actions.Remove"), () -> {
            tactic.getTechniques().remove(technique);
            refresh();
        });

        var popup = uiComponents.create(PopupButton.class);
        popup.setIcon(JmixIcon.ANGLE_DOWN.iconName());

        var popupContent = uiComponents.create(VBoxLayout.class);
        popupContent.add(deleteBtn);
        popupContent.add(descriptionLabel);
        popupContent.setSpacing(true);
        popup.setPopupContent(popupContent);

        var techniqueBox = uiComponents.create(HBoxLayout.class);
        techniqueBox.add(label);
        techniqueBox.add(popup);
        techniqueBox.setSpacing(true);

        return techniqueBox;
    }

    PopupButton tacticToAddTechniquesBtn(ScenarioTactic tactic) {
        var popup = uiComponents.create(PopupButton.class);
        popup.setCaption(messages.getMessage("actions.Add"));
        popup.setIcon(JmixIcon.ADD_ACTION.iconName());

        var popupContent = uiComponents.create(ScrollBoxLayout.class);
        popupContent.setContentMaxHeight("400px");

        stream(Technique.values())
                .filter(tech -> notIn(tactic, tech))
                .map(tech -> techniqueToBtn(tactic, tech))
                .forEach(popupContent::add);

        popup.setPopupContent(popupContent);

        return popup;
    }

    Button techniqueToBtn(ScenarioTactic tactic, Technique technique) {
        return btn(messages.getMessage("ru.sstu.ifbs.entity", "Technique." + technique.getId()), () -> {
            tactic.getTechniques().add(toScenario(tactic, technique));
            refresh();
        });
    }

    private Button tacticToBtn(Tactic tactic) {
        return btn(messages.getMessage("ru.sstu.ifbs.entity", "Tactic." + tactic.getId()), () -> {
            tacticsDc.getMutableItems().add(toScenario(tactic));
            refresh();
        });
    }

    private Label<String> label(String value) {
        var label = uiComponents.create(Label.TYPE_STRING);
        label.setValue(value);
        return label;
    }

    private Button btn(String caption, Runnable onClick) {
        var btn = uiComponents.create(Button.class);
        btn.setCaption(caption);
        btn.addClickListener(ev -> onClick.run());
        return btn;
    }

    private boolean notIn(ScenarioTactic tactic, Technique technique) {
        return tactic.getTechniques().stream()
                .map(ScenarioTechnique::getValue)
                .noneMatch(isEqual(technique));
    }

    private boolean notInDc(Tactic tactic) {
        return tacticsDc.getItems().stream()
                .map(ScenarioTactic::getValue)
                .noneMatch(isEqual(tactic));
    }

    private ScenarioTactic toScenario(Tactic tactic) {
        var scenarioTactic = dataContext.create(ScenarioTactic.class);
        scenarioTactic.setThreatScenario(threatScenarioDc.getItem());
        scenarioTactic.setValue(tactic);
        return scenarioTactic;
    }

    private ScenarioTechnique toScenario(ScenarioTactic tactic, Technique technique) {
        var scenarioTechnique = dataContext.create(ScenarioTechnique.class);
        scenarioTechnique.setTactic(tactic);
        scenarioTechnique.setValue(technique);
        return scenarioTechnique;
    }
}