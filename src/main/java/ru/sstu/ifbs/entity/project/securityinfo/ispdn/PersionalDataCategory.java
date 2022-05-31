package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PersionalDataCategory implements EnumClass<String> {

    SPECIAL("Special"),
    BIOMETRIC("Biometric"),
    PUBLIC("Public"),
    OTHER("Other");

    private String id;

    PersionalDataCategory(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PersionalDataCategory fromId(String id) {
        for (PersionalDataCategory at : PersionalDataCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}