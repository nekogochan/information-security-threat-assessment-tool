package ru.sstu.ifbs.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_THREAT_SCENARIO", indexes = {
        @Index(name = "IDX_THREATSCENARIO_THREAT_ID", columnList = "THREAT_ID")
})
@Entity(name = "gwf_ThreatScenario")
public class ThreatScenario extends DefaultNamedEntity {

    @NotNull
    @JoinColumn(name = "THREAT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Threat threat;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "threatScenario")
    private List<ScenarioTactic> tactics = new ArrayList<>();

    public List<ScenarioTactic> getTactics() {
        return tactics;
    }

    public void setTactics(List<ScenarioTactic> tactics) {
        this.tactics = tactics;
    }

    public Threat getThreat() {
        return threat;
    }

    public void setThreat(Threat threat) {
        this.threat = threat;
    }
}