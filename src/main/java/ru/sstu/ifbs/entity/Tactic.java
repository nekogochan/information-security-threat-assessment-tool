package ru.sstu.ifbs.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;


public enum Tactic implements EnumClass<String> {

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
    private final List<Technique> techniques;

    Tactic(String value, List<Technique> techniques) {
        this.id = value;
        this.techniques = techniques;
    }

    Tactic(String value) {
        this(value, Arrays.stream(Technique.values()).filter(t -> t.getId().startsWith(value)).toList());
    }

    public String getId() {
        return id;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    @Nullable
    public static Tactic fromId(String id) {
        for (Tactic at : Tactic.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}