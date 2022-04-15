package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum ImpactSourceType implements EnumClass<String> {

    INNER("Inner"),
    OUTER("Outer");

    private final String id;

    ImpactSourceType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ImpactSourceType fromId(String id) {
        for (ImpactSourceType at : ImpactSourceType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}