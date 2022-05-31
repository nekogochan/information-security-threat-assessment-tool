package ru.sstu.ifbs.entity.project.securityinfo.common;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum SecurityClass implements EnumClass<String> {

    K1("K1"),
    K2("K2"),
    K3("K3");

    private String id;

    SecurityClass(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SecurityClass fromId(String id) {
        for (SecurityClass at : SecurityClass.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}