package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "GWF_SCENARIO_TECHNIQUE", indexes = {
        @Index(name = "IDX_SCENARIOTECHNIQUE", columnList = "THREAT_SCENARIO_ID"),
        @Index(name = "IDX_SCENARIOTECHNIQUE", columnList = "TACTIC_ID")
})
@Entity(name = "gwf_ScenarioTechnique")
public class ScenarioTechnique extends DefaultEntity {

    @InstanceName
    @NotNull
    @Column(name = "VALUE_", nullable = false)
    private String value;

    @JoinColumn(name = "TACTIC_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ScenarioTactic tactic;

    public ScenarioTactic getTactic() {
        return tactic;
    }

    public void setTactic(ScenarioTactic tactic) {
        this.tactic = tactic;
    }

    public Technique getValue() {
        return value == null ? null : Technique.fromId(value);
    }

    public void setValue(Technique value) {
        this.value = value == null ? null : value.getId();
    }
}