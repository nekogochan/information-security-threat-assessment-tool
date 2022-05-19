package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@JmixEntity
@Table(name = "GWF_TECHNIQUE")
@Entity(name = "gwf_Technique")
public class Technique extends DefaultNamedEntity {
    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    private String code;

    @JoinColumn(name = "TACTIC_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Tactic tactic;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Tactic getTactic() {
        return tactic;
    }

    public void setTactic(Tactic tactic) {
        this.tactic = tactic;
    }
}