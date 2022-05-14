package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@JmixEntity
@Table(name = "GWF_IMPACT_TARGET")
@Entity(name = "gwf_ImpactTarget")
public class ImpactTarget extends DefaultNamedEntity {
}