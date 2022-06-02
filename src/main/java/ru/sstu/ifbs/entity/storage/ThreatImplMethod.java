package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "GWF_THREAT_IMPL_METHOD", indexes = {
        @Index(name = "IDX_THREATIMPLMETHOD", columnList = "SOURCE_ID"),
        @Index(name = "IDX_THREATIMPLMETHOD", columnList = "TARGET_ID")
})
@Entity(name = "gwf_ThreatImplMethod")
public class ThreatImplMethod extends DefaultEntity {

    @JoinColumn(name = "SOURCE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ImpactSource source;

    @JoinColumn(name = "TARGET_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ImpactTarget target;

    @NotNull
    @Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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