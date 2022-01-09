package ru.milovtim.bondschedule;

public interface BondInfoStorage {

    void saveBondInfo(Bond bond);

    Bond loadBondInfo(ISIN isin);
}
