package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_THREAT_IMPL_METHODS_LINK")
@Entity(name = "gwf_ThreatImplMethodsLink")
public class ThreatImplMethodsLink extends DefaultEntity {

    @JoinColumn(name = "SOURCE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ImpactSource source;

    @JoinTable(name = "GWF_THREAT_IMPL_METHODS_LINK_IMPACT_TARGET_LINK",
            joinColumns = @JoinColumn(name = "THREAT_IMPL_METHODS_LINK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "IMPACT_TARGET_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ImpactTarget> targets = new ArrayList<>();

    @JoinTable(name = "GWF_THR_IMPL_METH_LINKS_LINK",
            joinColumns = @JoinColumn(name = "THREAT_IMPL_METHODS_LINK_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "THREAT_IMPL_METHOD_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ThreatImplMethod> implMethods = new ArrayList<>();

    public void setTargets(List<ImpactTarget> targets) {
        this.targets = targets;
    }

    public List<ImpactTarget> getTargets() {
        return targets;
    }

    public List<ThreatImplMethod> getImplMethods() {
        return implMethods;
    }

    public void setImplMethods(List<ThreatImplMethod> implMethods) {
        this.implMethods = implMethods;
    }

    public ImpactSource getSource() {
        return source;
    }

    public void setSource(ImpactSource source) {
        this.source = source;
    }
}