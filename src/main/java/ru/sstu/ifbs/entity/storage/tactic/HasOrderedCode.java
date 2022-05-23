package ru.sstu.ifbs.entity.storage.tactic;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.stream.Collectors;

public interface HasOrderedCode extends Comparable<HasOrderedCode> {
    String getCode();

    void setCode(String code);

    @Override
    default int compareTo(@Nullable HasOrderedCode that) {
        if (that == null) {
            return 1;
        }
        var thisValue = getCodeValue(this.getCode());
        var thatValue = getCodeValue(that.getCode());
        return Integer.compare(thisValue, thatValue);
    }

    private int getCodeValue(String code) {
        return Integer.parseInt(
                Arrays.stream(code.split("\\."))
                        .skip(1)
                        .collect(Collectors.joining("")));
    }
}
