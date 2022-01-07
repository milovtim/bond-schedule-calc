package ru.milovtim.bondschedule.moex;

import ru.milovtim.bondschedule.Bond;
import ru.milovtim.bondschedule.BondImpl;
import ru.milovtim.bondschedule.BondInfoService;
import ru.milovtim.bondschedule.ISIN;
import ru.milovtim.bondschedule.adapter.MoexIssBondAdapter;

import java.util.Map;

public class MoexBondInfoService implements BondInfoService {
    private static final Map<String, Object> DEFAULT_QUERY_MAP = Map.of(
            "lang", "ru",
            "iss.meta", "off",
            "iss.only", "description"
    );

    private final InfoStatService infoStatService;

    public MoexBondInfoService(InfoStatService infoStatService) {
        this.infoStatService = infoStatService;
    }


    @Override
    public Bond getBondInfo(ISIN isin) {
        MoexIssInfo secInfo = infoStatService.getSecurityInfo(isin, DEFAULT_QUERY_MAP);
        MoexIssBondAdapter adapter = new MoexIssBondAdapter(secInfo);
        return new BondImpl(adapter);
    }
}
