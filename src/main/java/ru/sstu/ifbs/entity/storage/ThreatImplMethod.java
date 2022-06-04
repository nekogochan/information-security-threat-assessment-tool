package ru.sstu.ifbs.entity.storage;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@JmixEntity
@Table(name = "GWF_THREAT_IMPL_METHOD")
@Entity(name = "gwf_ThreatImplMethod")
public class ThreatImplMethod extends DefaultNamedEntity {
}