package ru.milovtim.bondschedule;

import lombok.experimental.Delegate;


public class BondImpl implements Bond {
    private final @Delegate Bond adapter;

    public BondImpl(Bond adapter) {
        this.adapter = adapter;
    }
}
