package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PersonalDataSubjectType implements EnumClass<String> {

    EMPLOYEES("Employees"),
    EXTERNAL_USERS("External users");

    private String id;

    PersonalDataSubjectType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PersonalDataSubjectType fromId(String id) {
        for (PersonalDataSubjectType at : PersonalDataSubjectType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}