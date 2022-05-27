package ru.sstu.ifbs.backoffice.collectors;

import io.jmix.ui.component.VBoxLayout;
import org.springframework.stereotype.Component;

@Component
public class VBoxCollector extends ComponentCollector<VBoxLayout> {
    public VBoxCollector() {
        super(VBoxLayout.class);
    }
}
