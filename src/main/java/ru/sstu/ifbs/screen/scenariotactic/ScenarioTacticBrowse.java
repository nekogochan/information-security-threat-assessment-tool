package ru.sstu.ifbs.screen.scenariotactic;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ScenarioTactic;

@UiController("gwf_ScenarioTactic.browse")
@UiDescriptor("scenario-tactic-browse.xml")
@LookupComponent("scenarioTacticsTable")
public class ScenarioTacticBrowse extends StandardLookup<ScenarioTactic> {
}