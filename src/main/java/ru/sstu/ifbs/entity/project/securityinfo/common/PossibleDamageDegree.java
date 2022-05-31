package ru.sstu.ifbs.entity.project.securityinfo.common;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@JmixEntity(name = "gwf_PossibleDamageDegree")
@Embeddable
public class PossibleDamageDegree {

    @Column(name = "CONFIDENTIALITY")
    private String confidentiality;

    @Column(name = "INTEGRITY")
    private String integrity;

    @Column(name = "ACCESSIBILITY")
    private String accessibility;

    public DamageDegreeLevel getAccessibility() {
        return accessibility == null ? null : DamageDegreeLevel.fromId(accessibility);
    }

    public void setAccessibility(DamageDegreeLevel accessibility) {
        this.accessibility = accessibility == null ? null : accessibility.getId();
    }

    public DamageDegreeLevel getIntegrity() {
        return integrity == null ? null : DamageDegreeLevel.fromId(integrity);
    }

    public void setIntegrity(DamageDegreeLevel integrity) {
        this.integrity = integrity == null ? null : integrity.getId();
    }

    public DamageDegreeLevel getConfidentiality() {
        return confidentiality == null ? null : DamageDegreeLevel.fromId(confidentiality);
    }

    public void setConfidentiality(DamageDegreeLevel confidentiality) {
        this.confidentiality = confidentiality == null ? null : confidentiality.getId();
    }
}