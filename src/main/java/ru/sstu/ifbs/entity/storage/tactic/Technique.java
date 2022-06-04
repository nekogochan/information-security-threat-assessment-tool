package ru.sstu.ifbs.entity.storage.tactic;

import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JmixEntity
@Table(name = "GWF_TECHNIQUE")
@Entity(name = "gwf_Technique")
public class Technique extends DefaultEntity implements HasOrderedCode {

    @InstanceName
    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "CODE", nullable = false, unique = true)
    @Pattern(message = "{msg://ru.sstu.ifbs.entity.storage.tactic/Technique.code.validation.Pattern}", regexp = "[a-zA-Zа-яА-Я\\d]+\\.[\\d.]+")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}