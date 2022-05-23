package ru.sstu.ifbs.entity.project;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_ACTUAL_THREAT", indexes = {
        @Index(name = "IDX_ACTUALTHREAT_THREAT_ID", columnList = "THREAT_ID"),
        @Index(name = "IDX_ACTUALTHREAT_PROJECT_ID", columnList = "PROJECT_ID")
})
@Entity(name = "gwf_ActualThreat")
public class ActualThreat extends DefaultEntity {

    @NotNull
    @JoinColumn(name = "THREAT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @InstanceName
    private Threat threat;

    @JoinTable(name = "GWF_ACTUAL_THREAT_SCEN_LINK",
            joinColumns = @JoinColumn(name = "ACTUAL_THREAT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "THREAT_SCENARIO_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ThreatScenario> scenarios = new ArrayList<>();

    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ThreatScenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ThreatScenario> scenarios) {
        this.scenarios = scenarios;
    }

    public Threat getThreat() {
        return threat;
    }

    public void setThreat(Threat threat) {
        this.threat = threat;
    }
}