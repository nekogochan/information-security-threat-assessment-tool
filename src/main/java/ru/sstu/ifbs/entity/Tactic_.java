package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;


public enum Tactic_ implements EnumClass<String> {

    T_1("T_1"),
    T_2("T_2"),
    T_3("T_3"),
    T_4("T_4"),
    T_5("T_5"),
    T_6("T_6"),
    T_7("T_7"),
    T_8("T_8"),
    T_9("T_9"),
    T_10("T_10");

    private final String id;
    private final List<Technique_> techniques;

    Tactic_(String value, List<Technique_> techniques) {
        this.id = value;
        this.techniques = techniques;
    }

    Tactic_(String value) {
        this(value, Arrays.stream(Technique_.values()).filter(t -> t.getId().startsWith(value)).toList());
    }

    public String getId() {
        return id;
    }

    public List<Technique_> getTechniques() {
        return techniques;
    }

    public String getName() {
        return "name";
    }

    public String getCode() {
        return "code";
    }

    public String getDescription() {
        return "description";
    }

    @Nullable
    public static Tactic_ fromId(String id) {
        for (Tactic_ at : Tactic_.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}