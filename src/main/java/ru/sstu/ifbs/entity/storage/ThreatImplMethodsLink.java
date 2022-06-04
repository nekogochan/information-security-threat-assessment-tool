package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@JmixEntity
@Table(name = "GWF_THREAT_IMPL_METHODS_LINK")
@Entity(name = "gwf_ThreatImplMethodsLink")
public class ThreatImplMethodsLink extends DefaultEntity {

    @JoinColumn(name = "SOURCE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ImpactSource source;

    @JoinColumn(name = "TARGET_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ImpactTarget target;

    @JoinTable(name = "GWF_THR_IMPL_METH_LINKS_LINK",
            joinColumns = @JoinColumn(name = "THREAT_IMPL_METHODS_LINK_ID"),
            inverseJoinColumns = @JoinColumn(name = "THREAT_IMPL_METHOD_ID"))
    @ManyToMany
    private List<ThreatImplMethod> implMethods;

    public List<ThreatImplMethod> getImplMethods() {
        return implMethods;
    }

    public void setImplMethods(List<ThreatImplMethod> implMethods) {
        this.implMethods = implMethods;
    }

    public ImpactTarget getTarget() {
        return target;
    }

    public void setTarget(ImpactTarget target) {
        this.target = target;
    }

    public ImpactSource getSource() {
        return source;
    }

    public void setSource(ImpactSource source) {
        this.source = source;
    }
}