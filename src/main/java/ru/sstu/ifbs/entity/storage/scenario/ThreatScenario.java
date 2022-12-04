package ru.sstu.ifbs.entity.storage.scenario;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;
import ru.sstu.ifbs.entity.storage.ImpactSource;
import ru.sstu.ifbs.entity.storage.Threat;
import ru.sstu.ifbs.entity.storage.ThreatImplMethod;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_THREAT_SCENARIO", indexes = {
        @Index(name = "IDX_THREATSCENARIO_THREAT_ID", columnList = "THREAT_ID"),
        @Index(name = "IDX_THREATSCENARIO", columnList = "IMPL_METHOD_ID")
})
@Entity(name = "gwf_ThreatScenario")
public class ThreatScenario extends DefaultEntity {

    @NotNull
    @JoinColumn(name = "THREAT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Threat threat;

    @JoinTable(name = "GWF_THREAT_SCENARIO_IMPACT_SOURCE_LINK",
            joinColumns = @JoinColumn(name = "THREAT_SCENARIO_ID"),
            inverseJoinColumns = @JoinColumn(name = "IMPACT_SOURCE_ID"))
    @ManyToMany
    private List<ImpactSource> sources;

    @JoinColumn(name = "IMPL_METHOD_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ThreatImplMethod implMethod;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "threatScenario")
    private List<ScenarioTactic> tactics = new ArrayList<>();

    @InstanceName
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ThreatImplMethod getImplMethod() {
        return implMethod;
    }

    public void setImplMethod(ThreatImplMethod implMethod) {
        this.implMethod = implMethod;
    }

    public List<ImpactSource> getSources() {
        return sources;
    }

    public void setSources(List<ImpactSource> sources) {
        this.sources = sources;
    }

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