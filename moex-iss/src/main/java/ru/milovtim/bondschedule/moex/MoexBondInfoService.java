package ru.milovtim.bondschedule.moex;

import ru.milovtim.bondschedule.Bond;
import ru.milovtim.bondschedule.BondImpl;
import ru.milovtim.bondschedule.BondInfoService;
import ru.milovtim.bondschedule.ISIN;

import java.util.LinkedHashMap;
import java.util.Map;

public class MoexBondInfoService implements BondInfoService {
    // to be able to test mocked http client need to fix query params order, so using LHMap here
    private static final Map<String, Object> DEFAULT_QUERY_MAP = new LinkedHashMap<>(3);
    static {
        DEFAULT_QUERY_MAP.put("lang", "ru");
        DEFAULT_QUERY_MAP.put("iss.meta", "off");
        DEFAULT_QUERY_MAP.put("iss.only", "description");
    }

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
