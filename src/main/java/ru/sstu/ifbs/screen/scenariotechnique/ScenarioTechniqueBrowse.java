package ru.sstu.ifbs.screen.scenariotechnique;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ScenarioTechnique;

@UiController("gwf_ScenarioTechnique.browse")
@UiDescriptor("scenario-technique-browse.xml")
@LookupComponent("scenarioTechniquesTable")
public class ScenarioTechniqueBrowse extends StandardLookup<ScenarioTechnique> {
}