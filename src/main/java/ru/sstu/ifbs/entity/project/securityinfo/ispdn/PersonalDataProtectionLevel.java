package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum PersonalDataProtectionLevel implements EnumClass<String> {

    PL1("PL1"),
    PL2("PL2"),
    PL3("PL3"),
    PL4("PL4");

    private final String id;

    PersonalDataProtectionLevel(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static PersonalDataProtectionLevel fromId(String id) {
        for (PersonalDataProtectionLevel at : PersonalDataProtectionLevel.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}