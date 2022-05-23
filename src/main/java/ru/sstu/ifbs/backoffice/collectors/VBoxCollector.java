package ru.sstu.ifbs.backoffice.collectors;

import io.jmix.ui.component.VBoxLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class VBoxCollector extends BoxCollector<VBoxLayout> {
    public VBoxCollector() {
        super(VBoxLayout.class);
    }
}
