package ru.milovtim.bondschedule.moex

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import feign.Feign
import feign.http2client.Http2Client
import feign.jackson.JacksonDecoder
import ru.milovtim.bondschedule.BondInfoService
import ru.milovtim.bondschedule.ISIN
import spock.lang.Specification

class MoexBondInfoServiceTest extends Specification {

    InfoStatService statService
    BondInfoService bondInfoService
    ObjectMapper om = new ObjectMapper()
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY)

    void setup() {
        //noinspection HttpUrlsUsage
        statService = Feign.builder()
                .client(new Http2Client())
                .decoder(new JacksonDecoder())
                .target(InfoStatService, "http://iss.moex.com")
        bondInfoService = new MoexBondInfoService(statService)
    }

    def "GetBondInfo"() {
        given:
        def isin = ISIN.of('RU000A1037L9')

        when:
        def res = bondInfoService.getBondInfo(isin)

        then:
        new File("./RU000A1037L9.json").with {
            om.writeValue(it, res)
        }
    }
}
