package ru.sstu.ifbs.screen.scenariotactic;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ScenarioTactic;

@UiController("gwf_ScenarioTactic.edit")
@UiDescriptor("scenario-tactic-edit.xml")
@EditedEntityContainer("scenarioTacticDc")
public class ScenarioTacticEdit extends StandardEditor<ScenarioTactic> {
}