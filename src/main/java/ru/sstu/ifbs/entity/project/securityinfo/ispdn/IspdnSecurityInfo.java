package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.project.securityinfo.ProjectSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.common.PossibleDamageDegree;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;

import javax.persistence.*;

@JmixEntity
@Table(name = "GWF_ISPDN_SECURITY_INFO")
@Entity(name = "gwf_IspdnSecurityInfo")
public class IspdnSecurityInfo extends ProjectSecurityInfo {

    @Column(name = "SYSTEM_SCALE")
    private String systemScale;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "confidentiality", column = @Column(name = "POSIBLE_DAMAGE_DEGREE_CONFIDENTIALITY")),
            @AttributeOverride(name = "integrity", column = @Column(name = "POSIBLE_DAMAGE_DEGREE_INTEGRITY")),
            @AttributeOverride(name = "accessibility", column = @Column(name = "POSIBLE_DAMAGE_DEGREE_ACCESSIBILITY"))
    })
    private PossibleDamageDegree possibleDamageDegree;

    @Column(name = "SECURITY_CLASS")
    private String securityClass;

    @Column(name = "ACTUAL_THREATS_TYPE")
    private String actualThreatsType;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "protectionLevel", column = @Column(name = "PERSIONAL_DATA_PROTECTION_LEVEL")),
            @AttributeOverride(name = "category", column = @Column(name = "PERSIONAL_DATA_CATEGORY")),
            @AttributeOverride(name = "subjectType", column = @Column(name = "PERSIONAL_DATA_SUBJECT_TYPE")),
            @AttributeOverride(name = "count", column = @Column(name = "PERSIONAL_DATA_COUNT_"))
    })
    private PersionalData persionalData;

    public PersionalData getPersionalData() {
        return persionalData;
    }

    public void setPersionalData(PersionalData persionalData) {
        this.persionalData = persionalData;
    }

    public ActualThreatsType getActualThreatsType() {
        return actualThreatsType == null ? null : ActualThreatsType.fromId(actualThreatsType);
    }

    public void setActualThreatsType(ActualThreatsType actualThreatsType) {
        this.actualThreatsType = actualThreatsType == null ? null : actualThreatsType.getId();
    }

    public SecurityClass getSecurityClass() {
        return securityClass == null ? null : SecurityClass.fromId(securityClass);
    }

    public void setSecurityClass(SecurityClass securityClass) {
        this.securityClass = securityClass == null ? null : securityClass.getId();
    }

    public PossibleDamageDegree getPossibleDamageDegree() {
        return possibleDamageDegree;
    }

    public void setPossibleDamageDegree(PossibleDamageDegree possibleDamageDegree) {
        this.possibleDamageDegree = possibleDamageDegree;
    }

    public SystemScale getSystemScale() {
        return systemScale == null ? null : SystemScale.fromId(systemScale);
    }

    public void setSystemScale(SystemScale systemScale) {
        this.systemScale = systemScale == null ? null : systemScale.getId();
    }
}