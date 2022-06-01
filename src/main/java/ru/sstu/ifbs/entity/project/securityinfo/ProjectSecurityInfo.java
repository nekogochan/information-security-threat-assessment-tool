package ru.sstu.ifbs.entity.project.securityinfo;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;

import javax.persistence.*;

import static javax.persistence.InheritanceType.JOINED;
import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

@JmixEntity
@Table(name = "GWF_PROJECT_SECURITY_INFO")
@Entity(name = "gwf_ProjectSecurityInfo")
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "PROJECT_SECURITY_INFO_TYPE")
public class ProjectSecurityInfo extends DefaultEntity {
}