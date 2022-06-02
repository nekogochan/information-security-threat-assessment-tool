package ru.sstu.ifbs.entity.storage.measures;

import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_SECURITY_MEASURE_GROUP")
@Entity(name = "gwf_SecurityMeasureGroup")
public class SecurityMeasureGroup extends DefaultEntity implements Comparable<SecurityMeasureGroup> {

    @InstanceName
    @Column(name = "NAME", nullable = false, unique = true)
    @NotNull
    private String name;

    @PositiveOrZero(message = "{msg://ru.sstu.ifbs.entity.storage.measures/SecurityMeasureGroup.orderNumber.validation.PositiveOrZero}")
    @Column(name = "ORDER_NUMBER", nullable = false)
    @NotNull
    private Integer orderNumber;

    @Composition
    @OneToMany(mappedBy = "securityMeasureGroup")
    private List<SecurityMeasure> measures = new ArrayList<>();

    public List<SecurityMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<SecurityMeasure> measures) {
        this.measures = measures;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(SecurityMeasureGroup that) {
        return Integer.compare(this.orderNumber, that.orderNumber);
    }
}