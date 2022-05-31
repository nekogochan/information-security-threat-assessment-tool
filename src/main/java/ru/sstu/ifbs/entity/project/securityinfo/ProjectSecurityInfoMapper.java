package ru.sstu.ifbs.entity.project.securityinfo;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum ProjectSecurityInfoMapper implements EnumClass<String> {

    ISPDN_SECURITY_INFO("ISPDN_SECURITY_INFO"),
    GIS_SECURITY_INFO("GIS_SECURITY_INFO");

    private String id;

    ProjectSecurityInfoMapper(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static ProjectSecurityInfoMapper fromId(String id) {
        for (ProjectSecurityInfoMapper at : ProjectSecurityInfoMapper.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}