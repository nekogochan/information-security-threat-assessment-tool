package ru.sstu.ifbs.entity.storage.measures;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.project.securityinfo.ispdn.PersonalDataProtectionLevel;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.tactic.HasOrderedCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JmixEntity
@Table(name = "GWF_SECURITY_MEASURE", indexes = {
        @Index(name = "IDX_SECURITYMEASURE", columnList = "SECURITY_MEASURE_GROUP_ID"),
        @Index(name = "IDX_SECURITYMEASURE_THREAT_ID", columnList = "THREAT_ID")
})
@Entity(name = "gwf_SecurityMeasures")
public class SecurityMeasure extends DefaultEntity implements HasOrderedCode {

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = "CODE", nullable = false, unique = true)
    @Pattern(message = "{msg://ru.sstu.ifbs.entity.storage.tactic/Tactic.code.validation.Pattern}", regexp = "[a-zA-Z\u0430-\u044F\u0410-\u042F\\\\d]+\\\\.[\\\\d.]+")
    @NotNull
    private String code;

    @NotNull
    @Column(name = "PROTECTION_LEVEL", nullable = false)
    private String protectionLevel;

    @JoinColumn(name = "SECURITY_MEASURE_GROUP_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SecurityMeasureGroup securityMeasureGroup;
    @JoinColumn(name = "THREAT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Threat threat;

    public Threat getThreat() {
        return threat;
    }

    public void setThreat(Threat threat) {
        this.threat = threat;
    }

    public SecurityMeasureGroup getSecurityMeasureGroup() {
        return securityMeasureGroup;
    }

    public void setSecurityMeasureGroup(SecurityMeasureGroup securityMeasureGroup) {
        this.securityMeasureGroup = securityMeasureGroup;
    }

    public void setProtectionLevel(PersonalDataProtectionLevel protectionLevel) {
        this.protectionLevel = protectionLevel == null ? null : protectionLevel.getId();
    }

    public PersonalDataProtectionLevel getProtectionLevel() {
        return protectionLevel == null ? null : PersonalDataProtectionLevel.fromId(protectionLevel);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}