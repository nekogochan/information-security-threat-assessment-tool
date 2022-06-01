package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PersonalDataCategory implements EnumClass<String> {

    SPECIAL("Special"),
    BIOMETRIC("Biometric"),
    PUBLIC("Public"),
    OTHER("Other");

    private String id;

    PersonalDataCategory(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PersonalDataCategory fromId(String id) {
        for (PersonalDataCategory at : PersonalDataCategory.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}