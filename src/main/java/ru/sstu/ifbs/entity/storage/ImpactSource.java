package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "GWF_IMPACT_SOURCE")
@Entity(name = "gwf_ImpactSource")
public class ImpactSource extends DefaultNamedEntity {

    @NotNull
    @Column(name = "TYPE_", nullable = false)
    private String type;

    @NotNull
    @Column(name = "LEVEL_", nullable = false)
    private Integer level;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public ImpactSourceType getType() {
        return type == null ? null : ImpactSourceType.fromId(type);
    }

    public void setType(ImpactSourceType type) {
        this.type = type == null ? null : type.getId();
    }
}