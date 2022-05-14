package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Entity(name = "gwf_Tactic")
public class Tactic extends DefaultNamedEntity {

    @Column(name = "CODE", nullable = false, unique = true)
    @NotNull
    private String code;

    @OneToMany(mappedBy = "tactic")
    private List<Technique> techniques = new ArrayList<>();

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}