package ru.milovtim.bondschedule.storage


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import ru.milovtim.bondschedule.Bond
import ru.milovtim.bondschedule.BondInfoStorage
import spock.lang.Shared
import spock.lang.Specification

class YCBondInfoStorageTest extends Specification {

    @Shared
    BondInfoStorage tested = new YCBondInfoStorage('test-unit1');
    Bond bond


    void setup() {
        def res = getClass().getResource('/RU000A101GH4.json')
        bond = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .readerFor(BondPOJO)
                .readValue(res)
    }

    def "SaveBondInfo"() {
        when:
        tested.saveBondInfo(bond)

        then:
        true
    }
}
