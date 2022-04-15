package ru.sstu.ifbs.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JmixEntity
@Table(name = "GWF_SCENARIO_TACTIC", indexes = {
        @Index(name = "IDX_SCENARIOTACTIC", columnList = "TECHNIQUE_ID")
})
@Entity(name = "gwf_ScenarioTactic")
public class ScenarioTactic {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "TECHNIQUE_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ScenarioTechnique technique;

    @InstanceName
    @NotNull
    @Column(name = "VALUE_", nullable = false)
    private String value;

    public Tactic getValue() {
        return value == null ? null : Tactic.fromId(value);
    }

    public void setValue(Tactic value) {
        this.value = value == null ? null : value.getId();
    }

    public ScenarioTechnique getTechnique() {
        return technique;
    }

    public void setTechnique(ScenarioTechnique technique) {
        this.technique = technique;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}