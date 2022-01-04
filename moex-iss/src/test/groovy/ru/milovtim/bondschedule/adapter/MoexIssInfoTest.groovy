package ru.milovtim.bondschedule.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification

class MoexIssInfoTest extends Specification {

    @Shared om = new ObjectMapper()

    def "test creation"() {
        given:
        def columns = ['one', 'two', 'three']
        def field = ['some', 'value', 'exists'] as String[]

        when:
        def tested = createSimpleInfo(columns, field)

        then:
        tested.getDescription().columns == columns
        tested.getDescription().data[0].fieldDescriptions == field
    }

    private MoexIssInfo createSimpleInfo(columns,field) {
        new MoexIssInfo().with { mi ->
            mi.description = new MoexIssInfo.Description().with { descr ->
                descr.columns = columns
                descr.data = [new MoexIssInfo.FieldData(field)]
                descr
            }
            mi
        }
    }

    def "test serialization"() {
        given:
        def tested = createSimpleInfo(['one', 'two', 'three'], ['1', '2', '3'] as String[])
        tested.getDescription().data << new MoexIssInfo.FieldData(['second', 'data', 'value'] as String[])

        when:
        def res = om.writeValueAsString(tested)

        then:
        res
        println res
    }

    def "test read json value"() {
        given:
        def om = new ObjectMapper()

        when:
        def result = getClass().getResource('/RU000A1037L9.json').withInputStream {
            om.readValue(it, MoexIssInfo)
        }

        then:
        result
    }
}
