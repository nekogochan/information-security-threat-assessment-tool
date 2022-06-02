package ru.sstu.ifbs.entity.project.securityinfo;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum ProjectSecurityInfoType implements EnumClass<String> {

    ISPDN_SECURITY_INFO("ISPDN_SECURITY_INFO"),
    GIS_SECURITY_INFO("GIS_SECURITY_INFO");

    private String id;

    ProjectSecurityInfoType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ProjectSecurityInfoType fromId(String id) {
        for (ProjectSecurityInfoType at : ProjectSecurityInfoType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}