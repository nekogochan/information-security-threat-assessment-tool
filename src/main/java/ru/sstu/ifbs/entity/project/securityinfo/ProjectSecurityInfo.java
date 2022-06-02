package ru.sstu.ifbs.entity.project.securityinfo;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.project.Project;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "GWF_PROJECT_SECURITY_INFO", indexes = {
        @Index(name = "IDX_PROJECTSECURITYINFO", columnList = "PROJECT_ID")
})
@Entity(name = "gwf_ProjectSecurityInfo")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PROJECT_SECURITY_INFO_TYPE", discriminatorType = DiscriminatorType.STRING)
public class ProjectSecurityInfo extends DefaultEntity {

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @NotNull
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @Composition
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}