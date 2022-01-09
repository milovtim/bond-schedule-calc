package ru.milovtim.bondschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of", onConstructor_= {@JsonCreator})
@EqualsAndHashCode(of = "value")
public class ISIN {
    @JsonValue
    private final String value;

    @Override
    public String toString() {
        return getValue();
    }
}
