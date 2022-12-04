package ru.sstu.ifbs.gui;

public abstract class Toggle {

    public abstract void onToggleOn();
    public abstract void onToggleOff();

    public static Toggle of(Runnable onOn, Runnable onOff) {
        return new Toggle() {
            @Override
            public void onToggleOn() {
                onOn.run();
            }

            @Override
            public void onToggleOff() {
                onOff.run();
            }
        };
    }

    private boolean isOn = false;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
        if (isOn) {
            onToggleOn();
        } else {
            onToggleOff();
        }
    }

    public void fire() {
        setOn(!isOn());
    }
}
