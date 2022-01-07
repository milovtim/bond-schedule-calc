package ru.milovtim.bondschedule.moex;

import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import ru.milovtim.bondschedule.ISIN;

import java.util.Map;

/**
 * MOEX ISS: docs are <a href="https://www.moex.com/a2193">here</a>
 */
public interface InfoStatService {

    /*
    http://iss.moex.com/iss/securities/RU000A1037L9.json?
      lang=ru
    & iss.meta=off
    & iss.only=description
    */
    @RequestLine("GET /iss/securities/{isin}.json")
    MoexIssInfo getSecurityInfo(@Param(value = "isin") ISIN isin, @QueryMap Map<String, Object> queryMap);
}
