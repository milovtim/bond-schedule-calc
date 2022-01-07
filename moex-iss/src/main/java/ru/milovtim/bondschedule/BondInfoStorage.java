package ru.milovtim.bondschedule;

interface BondInfoStorage {

    void saveBondInfo(BondImpl bond);

    BondImpl loadBondInfo(ISIN isin);
}
