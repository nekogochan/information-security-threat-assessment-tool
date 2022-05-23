package ru.sstu.ifbs.entity.storage.tactic;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JmixEntity
@Table(name = "GWF_TECHNIQUE")
@Entity(name = "gwf_Technique")
public class Technique extends DefaultNamedEntity implements HasOrderedCode {

    @Column(name = "CODE", nullable = false, unique = true)
    @Pattern(message = "{msg://ru.sstu.ifbs.entity.storage.tactic/Technique.code.validation.Pattern}", regexp = "[a-zA-Z\\d]+\\.[\\d.]+")
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