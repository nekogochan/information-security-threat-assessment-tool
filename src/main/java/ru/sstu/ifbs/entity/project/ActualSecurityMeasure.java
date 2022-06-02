package ru.sstu.ifbs.entity.project;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;
import ru.sstu.ifbs.entity.storage.measures.SecurityMeasure;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "GWF_ACTUAL_SECURITY_MEASURE", indexes = {
        @Index(name = "IDX_ACTUALSECURITYMEASURE", columnList = "VALUE_ID"),
        @Index(name = "IDX_ACTUALSECURITYMEASURE", columnList = "PROJECT_ID")
})
@Entity(name = "gwf_ActualSecurityMeasure")
public class ActualSecurityMeasure extends DefaultEntity implements Comparable<ActualSecurityMeasure> {

    @JoinColumn(name = "VALUE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private SecurityMeasure value;
    @JoinColumn(name = "PROJECT_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public SecurityMeasure getValue() {
        return value;
    }

    public void setValue(SecurityMeasure value) {
        this.value = value;
    }

    @Override
    public int compareTo(ActualSecurityMeasure that) {
        return this.value.compareTo(that.value);
    }
}