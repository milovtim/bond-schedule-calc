package ru.milovtim.bondschedule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public class ISIN {
    @JsonValue
    private final String value;

    @JsonCreator
    public ISIN(String value) {
        this.value = value;
    }
}
