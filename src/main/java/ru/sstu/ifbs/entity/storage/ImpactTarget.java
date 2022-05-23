package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@JmixEntity
@Table(name = "GWF_IMPACT_TARGET")
@Entity(name = "gwf_ImpactTarget")
public class ImpactTarget extends DefaultNamedEntity {
}