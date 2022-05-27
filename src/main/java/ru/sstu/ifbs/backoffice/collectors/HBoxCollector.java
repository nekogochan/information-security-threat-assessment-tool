package ru.sstu.ifbs.backoffice.collectors;

import io.jmix.ui.component.HBoxLayout;
import org.springframework.stereotype.Component;

@Component
public class HBoxCollector extends ComponentCollector<HBoxLayout> {
    public HBoxCollector() {
        super(HBoxLayout.class);
    }
}
