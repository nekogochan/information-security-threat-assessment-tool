package ru.sstu.ifbs.entity.storage.measures;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.project.securityinfo.common.SecurityClass;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.tactic.HasOrderedCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Comparator;

import static java.util.Comparator.comparing;

@JmixEntity
@Table(name = "GWF_SECURITY_MEASURE", indexes = {
        @Index(name = "IDX_SECURITYMEASURE", columnList = "SECURITY_MEASURE_GROUP_ID"),
        @Index(name = "IDX_SECURITYMEASURE_THREAT_ID", columnList = "THREAT_ID")
})
@Entity(name = "gwf_SecurityMeasures")
public class SecurityMeasure extends DefaultEntity implements HasOrderedCode {

    public final static Comparator<SecurityMeasure> COMPARATOR =
            comparing(SecurityMeasure::getSecurityMeasureGroup).thenComparing(it -> it);

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true)
    @NotNull
    private String name;

    @Column(name = "CODE", nullable = false, unique = true)
    @Pattern(message = "{msg://ru.sstu.ifbs.entity.storage.tactic/Tactic.code.validation.Pattern}", regexp = "[a-zA-Zа-яА-Я\\d]+\\.[\\d.]+")
    @NotNull
    private String code;

    @Column(name = "SECURITY_CLASS")
    private String securityClass;

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

    public void setSecurityClass(SecurityClass securityClass) {
        this.securityClass = securityClass == null ? null : securityClass.getId();
    }

    public SecurityClass getSecurityClass() {
        return securityClass == null ? null : SecurityClass.fromId(securityClass);
    }

    public SecurityMeasureGroup getSecurityMeasureGroup() {
        return securityMeasureGroup;
    }

    public void setSecurityMeasureGroup(SecurityMeasureGroup securityMeasureGroup) {
        this.securityMeasureGroup = securityMeasureGroup;
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