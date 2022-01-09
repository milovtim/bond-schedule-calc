package ru.milovtim.bondschedule.moex


import feign.Feign
import feign.jackson.JacksonDecoder
import feign.mock.HttpMethod
import feign.mock.MockClient
import feign.mock.MockTarget
import ru.milovtim.bondschedule.BondInfoService
import ru.milovtim.bondschedule.ISIN
import spock.lang.Specification

import java.time.LocalDate

class MoexBondInfoServiceTest extends Specification {

    MockClient mockClient
    InfoStatService statService
    BondInfoService bondInfoService

    def "GetBondInfo"() {
        given:
        def isin = ISIN.of('RU000A1037L9')
        def url = "/iss/securities/${isin}.json?lang=ru&iss.meta=off&iss.only=description"
        def respData = getClass().getResourceAsStream("/${isin}.json")

        mockClient = new MockClient().ok(HttpMethod.GET, url, respData)
        statService = Feign.builder()
                .client(mockClient)
                .decoder(new JacksonDecoder())
                .target(new MockTarget<>(InfoStatService))
        bondInfoService = new MoexBondInfoService(statService)

        when:
        def res = bondInfoService.getBondInfo(isin)

        then:
        mockClient.verifyOne(HttpMethod.GET, url)
        res.isin == isin.value
        res.couponDate == LocalDate.of(2022, 3, 8)
    }
}
