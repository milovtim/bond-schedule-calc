package ru.milovtim.bondschedule;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.experimental.Delegate;


@JsonInclude()
public class BondImpl implements Bond {
    private final @Delegate Bond adapter;

    public BondImpl(Bond adapter) {
        this.adapter = adapter;
    }
}
