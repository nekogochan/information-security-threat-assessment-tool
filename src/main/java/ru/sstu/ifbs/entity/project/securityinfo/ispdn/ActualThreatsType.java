package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum ActualThreatsType implements EnumClass<String> {

    T1("T1"),
    T2("T2"),
    T3("T3");

    private String id;

    ActualThreatsType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ActualThreatsType fromId(String id) {
        for (ActualThreatsType at : ActualThreatsType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}