package ru.milovtim.bondschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class ISIN {
    @JsonValue
    private final String value;

    @JsonCreator
    public static ISIN of(String val) {
        return new ISIN(val);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
