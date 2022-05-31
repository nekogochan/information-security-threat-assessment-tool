package ru.sstu.ifbs.entity.project.securityinfo.common;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum SystemScale implements EnumClass<String> {

    FEDERAL("Federal"),
    REGIONAL("Regional"),
    OBJECT("Object");

    private String id;

    SystemScale(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SystemScale fromId(String id) {
        for (SystemScale at : SystemScale.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}