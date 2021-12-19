package ru.milovtim.bondschedule;

public interface BondInfoService {

    /**
     * fetch bond info from information provider (i.e. MOEX or another)
     * @param isin isin code
     * @return Bond data
     */
    Bond getBondInfo(ISIN isin);
}
