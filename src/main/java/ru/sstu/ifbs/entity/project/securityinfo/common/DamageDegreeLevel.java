package ru.sstu.ifbs.entity.project.securityinfo.common;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum DamageDegreeLevel implements EnumClass<String> {

    LOW("Low"),
    MEDIUM("MEDIUM"),
    HIGH("HIGH");

    private String id;

    DamageDegreeLevel(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static DamageDegreeLevel fromId(String id) {
        for (DamageDegreeLevel at : DamageDegreeLevel.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}