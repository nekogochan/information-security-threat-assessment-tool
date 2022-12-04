package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;
import ru.sstu.ifbs.entity.storage.scenario.ThreatScenario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_THREAT")
@Entity(name = "gwf_Threat")
public class Threat extends DefaultNamedEntity {

    @JoinTable(name = "GWF_THREAT_IMPACT_SOURCES_LINK",
            joinColumns = @JoinColumn(name = "THREAT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "IMPACT_SOURCES_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ImpactSource> sources = new ArrayList<>();

    @JoinTable(name = "GWF_THREAT_IMPACT_TARGET_LINK",
            joinColumns = @JoinColumn(name = "THREAT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "IMPACT_TARGET_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ImpactTarget> targets = new ArrayList<>();

    @JoinTable(name = "GWF_THREAT_SECURITY_MEASURE_LINK",
            joinColumns = @JoinColumn(name = "THREAT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "SECURITY_MEASURE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<SecurityMeasure> securityMeasures;

    @OneToMany(mappedBy = "threat")
    private List<ThreatScenario> scenarios;

    public List<SecurityMeasure> getSecurityMeasures() {
        return securityMeasures;
    }

    public void setSecurityMeasures(List<SecurityMeasure> securityMeasures) {
        this.securityMeasures = securityMeasures;
    }

    public List<ThreatScenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<ThreatScenario> scenarios) {
        this.scenarios = scenarios;
    }

    public List<ImpactTarget> getTargets() {
        return targets;
    }

    public void setTargets(List<ImpactTarget> targets) {
        this.targets = targets;
    }

    public List<ImpactSource> getSources() {
        return sources;
    }

    public void setSources(List<ImpactSource> sources) {
        this.sources = sources;
    }
}