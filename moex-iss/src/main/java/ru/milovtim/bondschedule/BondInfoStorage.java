package ru.milovtim.bondschedule;

interface BondInfoStorage {

    void saveBondInfo(Bond bond);

    Bond loadBondInfo(ISIN isin);
}
