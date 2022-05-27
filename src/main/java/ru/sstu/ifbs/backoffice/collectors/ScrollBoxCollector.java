package ru.sstu.ifbs.backoffice.collectors;

import io.jmix.ui.UiComponents;
import io.jmix.ui.component.Component;
import io.jmix.ui.component.ComponentContainer;
import io.jmix.ui.component.ScrollBoxLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collector;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component(ScrollBoxCollector.NAME)
public class ScrollBoxCollector extends ComponentCollector<ScrollBoxLayout> {
    public static final String NAME = "gwf_ScrollBoxCollector";
    @Autowired
    private UiComponents uiComponents;

    public ScrollBoxCollector() {
        super(ScrollBoxLayout.class);
    }

    public <DC extends ComponentCollector<D>, D extends ComponentContainer>
    Collector<Component, D, ScrollBoxLayout> wrap(DC sourceCollector) {
        return Collectors.collectingAndThen(
                sourceCollector,
                it -> {
                    var scrollBox = uiComponents.create(ScrollBoxLayout.class);
                    scrollBox.setContentMaxHeight("400px");
                    scrollBox.add(it);
                    return scrollBox;
                }
        );
    }
}
