package ru.sstu.ifbs.screen.technique;

import io.jmix.ui.screen.*;
import ru.sstu.ifbs.entity.Technique;

@UiController("gwf_Technique.browse")
@UiDescriptor("technique-browse.xml")
@LookupComponent("techniquesTable")
public class TechniqueBrowse extends StandardLookup<Technique> {
}