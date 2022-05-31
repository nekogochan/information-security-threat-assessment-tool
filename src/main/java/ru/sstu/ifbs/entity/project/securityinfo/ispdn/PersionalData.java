package ru.sstu.ifbs.entity.project.securityinfo.ispdn;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.PositiveOrZero;

@JmixEntity(name = "gwf_PersionalData")
@Embeddable
public class PersionalData {

    @Column(name = "PROTECTION_LEVEL")
    private String protectionLevel;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "SUBJECT_TYPE")
    private String subjectType;

    @PositiveOrZero(message = "{msg://ru.sstu.ifbs.entity.project.securityinfo.ispdn/PersionalData.count.validation.PositiveOrZero}")
    @Column(name = "COUNT_")
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public PersonalDataSubjectType getSubjectType() {
        return subjectType == null ? null : PersonalDataSubjectType.fromId(subjectType);
    }

    public void setSubjectType(PersonalDataSubjectType subjectType) {
        this.subjectType = subjectType == null ? null : subjectType.getId();
    }

    public PersionalDataCategory getCategory() {
        return category == null ? null : PersionalDataCategory.fromId(category);
    }

    public void setCategory(PersionalDataCategory category) {
        this.category = category == null ? null : category.getId();
    }

    public PersonalDataProtectionLevel getProtectionLevel() {
        return protectionLevel == null ? null : PersonalDataProtectionLevel.fromId(protectionLevel);
    }

    public void setProtectionLevel(PersonalDataProtectionLevel protectionLevel) {
        this.protectionLevel = protectionLevel == null ? null : protectionLevel.getId();
    }
}