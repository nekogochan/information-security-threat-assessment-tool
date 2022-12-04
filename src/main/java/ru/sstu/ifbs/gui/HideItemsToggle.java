package ru.sstu.ifbs.gui;

import io.jmix.ui.component.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class HideItemsToggle extends Toggle {

    private final List<Component> components;
    private final List<Runnable> onOn = new ArrayList<>();
    private final List<Runnable> onOff = new ArrayList<>();

    public HideItemsToggle(Component... components) {
        this.components = asList(components);
    }

    public void addOnOnListener(Runnable onOn) {
        this.onOn.add(onOn);
    }

    public void addOnOffListener(Runnable onOff) {
        this.onOff.add(onOff);
    }

    @Override
    public void onToggleOn() {
        components.forEach(it -> it.setVisible(false));
        onOn.forEach(Runnable::run);
    }

    @Override
    public void onToggleOff() {
        components.forEach(it -> it.setVisible(true));
        onOff.forEach(Runnable::run);
    }
}
