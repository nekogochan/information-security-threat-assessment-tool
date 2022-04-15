package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum Technique implements EnumClass<String> {

    ;

    private final String id;

    Technique(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Technique fromId(String id) {
        for (Technique at : Technique.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}