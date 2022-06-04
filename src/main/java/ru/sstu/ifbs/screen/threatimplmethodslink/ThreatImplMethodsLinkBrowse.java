package ru.sstu.ifbs.screen.threatimplmethodslink;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.Label;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sstu.ifbs.entity.DefaultNamedEntity;
import ru.sstu.ifbs.entity.storage.ThreatImplMethodsLink;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

@UiController("gwf_ThreatImplMethodsLink.browse")
@UiDescriptor("threat-impl-methods-link-browse.xml")
@LookupComponent("threatImplMethodsTable")
public class ThreatImplMethodsLinkBrowse extends StandardLookup<ThreatImplMethodsLink> {

    @Autowired
    private UiComponents uiComponents;

    @Install(to = "threatImplMethodsTable.values", subject = "columnGenerator")
    private Component threatImplMethodsTableValuesColumnGenerator(ThreatImplMethodsLink threatImplMethodsLink) {
        var value = threatImplMethodsLink.getImplMethods().stream()
                .map(DefaultNamedEntity::getName)
                .collect(joining(", "));
        var label = uiComponents.create(Label.TYPE_STRING);
        label.setValue(value);
        return label;
    }
}