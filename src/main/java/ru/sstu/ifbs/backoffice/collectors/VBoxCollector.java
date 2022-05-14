package ru.sstu.ifbs.backoffice.collectors;

import io.jmix.ui.component.VBoxLayout;
import org.springframework.context.annotation.Scope;

@org.springframework.stereotype.Component
@Scope("prototype")
public class VBoxCollector extends BoxCollector<VBoxLayout> {
    public VBoxCollector() {
        super(VBoxLayout.class);
    }
}
