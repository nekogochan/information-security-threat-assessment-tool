package ru.sstu.ifbs.entity.storage.tactic;

import io.jmix.core.metamodel.annotation.JmixEntity;
import ru.sstu.ifbs.entity.DefaultNamedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@JmixEntity
@Table(name = "GWF_TACTIC")
@Entity(name = "gwf_Tactic")
public class Tactic extends DefaultNamedEntity implements HasOrderedCode {

    @Column(name = "CODE", nullable = false, unique = true)
    @Pattern(message = "{msg://ru.sstu.ifbs.entity.storage.tactic/Tactic.code.validation.Pattern}", regexp = "[a-zA-Zа-яА-Я\\d]+\\.[\\d.]+")
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