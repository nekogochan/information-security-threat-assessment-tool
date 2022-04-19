package ru.sstu.ifbs.screen.scenariotechnique;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.ScenarioTechnique;

@UiController("gwf_ScenarioTechnique.edit")
@UiDescriptor("scenario-technique-edit.xml")
@EditedEntityContainer("scenarioTechniqueDc")
public class ScenarioTechniqueEdit extends StandardEditor<ScenarioTechnique> {
}