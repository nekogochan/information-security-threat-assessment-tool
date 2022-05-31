package ru.sstu.ifbs.entity.project.securityinfo.gis;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.project.securityinfo.ProjectSecurityInfo;
import ru.sstu.ifbs.entity.project.securityinfo.common.PossibleDamageDegree;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.project.securityinfo.common.SystemScale;

import javax.persistence.*;

@JmixEntity
@Table(name = "GWF_GIS_SECURITY_INFO")
@Entity(name = "gwf_GisSecurityInfo")
public class GisSecurityInfo extends ProjectSecurityInfo {

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "confidentiality", column = @Column(name = "POSSIBLE_DAMAGE_DEGREE_CONFIDENTIALITY")),
            @AttributeOverride(name = "integrity", column = @Column(name = "POSSIBLE_DAMAGE_DEGREE_INTEGRITY")),
            @AttributeOverride(name = "accessibility", column = @Column(name = "POSSIBLE_DAMAGE_DEGREE_ACCESSIBILITY"))
    })
    private PossibleDamageDegree possibleDamageDegree;

    @Column(name = "SYSTEM_SCALE")
    private String systemScale;

    @Column(name = "SECURITY_CLASS")
    private String securityClass;

    @Column(name = "SIGNIFICANCE_LEVEL")
    private String significanceLevel;

    public SignificanceLevel getSignificanceLevel() {
        return significanceLevel == null ? null : SignificanceLevel.fromId(significanceLevel);
    }

    public void setSignificanceLevel(SignificanceLevel significanceLevel) {
        this.significanceLevel = significanceLevel == null ? null : significanceLevel.getId();
    }

    public SecurityClass getSecurityClass() {
        return securityClass == null ? null : SecurityClass.fromId(securityClass);
    }

    public void setSecurityClass(SecurityClass securityClass) {
        this.securityClass = securityClass == null ? null : securityClass.getId();
    }

    public SystemScale getSystemScale() {
        return systemScale == null ? null : SystemScale.fromId(systemScale);
    }

    public void setSystemScale(SystemScale systemScale) {
        this.systemScale = systemScale == null ? null : systemScale.getId();
    }

    public PossibleDamageDegree getPossibleDamageDegree() {
        return possibleDamageDegree;
    }

    public void setPossibleDamageDegree(PossibleDamageDegree possibleDamageDegree) {
        this.possibleDamageDegree = possibleDamageDegree;
    }
}