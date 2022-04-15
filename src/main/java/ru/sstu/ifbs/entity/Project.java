package ru.sstu.ifbs.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.annotation.DeletedBy;
import io.jmix.core.annotation.DeletedDate;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "GWF_PROJECT", indexes = {
        @Index(name = "IDX_PROJECT_GROUP_ID", columnList = "GROUP_ID")
})
@Entity(name = "gwf_Project")
public class Project {

    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedBy
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @DeletedBy
    @Column(name = "DELETED_BY")
    private String deletedBy;

    @DeletedDate
    @Column(name = "DELETED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;

    @JoinTable(name = "GWF_PROJECT_IMPACT_SOURCE_LINK",
            joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "IMPACT_SOURCE_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ImpactSource> impactSources;

    @JoinTable(name = "GWF_PROJECT_IMPACT_TARGET_LINK",
            joinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "IMPACT_TARGET_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<ImpactTarget> impactTargets;

    @NotNull
    @JoinColumn(name = "GROUP_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Group group;

    @InstanceName
    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "DESCRIPTION", nullable = false)
    @Lob
    private String description;

    @OnDelete(DeletePolicy.CASCADE)
    @Composition
    @OneToMany(mappedBy = "project")
    private List<ActualThreat> actualThreats;

    public List<ActualThreat> getActualThreats() {
        return actualThreats;
    }

    public void setActualThreats(List<ActualThreat> actualThreats) {
        this.actualThreats = actualThreats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<ImpactTarget> getImpactTargets() {
        return impactTargets;
    }

    public void setImpactTargets(List<ImpactTarget> impactTargets) {
        this.impactTargets = impactTargets;
    }

    public List<ImpactSource> getImpactSources() {
        return impactSources;
    }

    public void setImpactSources(List<ImpactSource> impactSources) {
        this.impactSources = impactSources;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}