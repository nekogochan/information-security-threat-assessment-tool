package ru.sstu.ifbs.entity.project.securityinfo.gis;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum SignificanceLevel implements EnumClass<String> {

    SL1("SL1"),
    SL2("SL2"),
    SL3("SL3");

    private String id;

    SignificanceLevel(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static SignificanceLevel fromId(String id) {
        for (SignificanceLevel at : SignificanceLevel.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}