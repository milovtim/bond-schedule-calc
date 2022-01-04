package ru.milovtim.bondschedule.adapter;

interface PojoAdapter<S, D> {
    S getSource();

    D createDestination();
}
