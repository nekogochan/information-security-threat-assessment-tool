package ru.sstu.ifbs.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "GWF_SCENARIO_TACTIC", indexes = {
        @Index(name = "IDX_SCENARIOTACTIC", columnList = "TECHNIQUE_ID"),
        @Index(name = "IDX_SCENARIOTACTIC", columnList = "THREAT_SCENARIO_ID")
})
@Entity(name = "gwf_ScenarioTactic")
public class ScenarioTactic {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @InstanceName
    @NotNull
    @Column(name = "VALUE_", nullable = false)
    private String value;

    @Composition
    @OneToMany(mappedBy = "tactic")
    private List<ScenarioTechnique> techniques = new ArrayList<>();

    @JoinColumn(name = "THREAT_SCENARIO_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ThreatScenario threatScenario;

    public ThreatScenario getThreatScenario() {
        return threatScenario;
    }

    public void setThreatScenario(ThreatScenario threatScenario) {
        this.threatScenario = threatScenario;
    }

    public List<ScenarioTechnique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<ScenarioTechnique> techniques) {
        this.techniques = techniques;
    }

    public Tactic getValue() {
        return value == null ? null : Tactic.fromId(value);
    }

    public void setValue(Tactic value) {
        this.value = value == null ? null : value.getId();
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}