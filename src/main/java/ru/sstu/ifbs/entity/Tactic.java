package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Tactic implements EnumClass<String> {

    ;

    private final String id;

    Tactic(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Tactic fromId(String id) {
        for (Tactic at : Tactic.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}