package ru.sstu.ifbs.screen.technique;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Technique;

@UiController("gwf_Technique.edit")
@UiDescriptor("technique-edit.xml")
@EditedEntityContainer("techniqueDc")
public class TechniqueEdit extends StandardEditor<Technique> {
}