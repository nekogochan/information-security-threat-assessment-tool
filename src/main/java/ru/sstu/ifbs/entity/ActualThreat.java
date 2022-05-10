package ru.sstu.ifbs.entity;

import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private List<ThreatScenario> scenarios;

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