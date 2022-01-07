package ru.milovtim.bondschedule.adapter

import com.fasterxml.jackson.databind.ObjectMapper
import ru.milovtim.bondschedule.moex.MoexIssInfo
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

    private MoexIssInfo createSimpleInfo(columns, field) {
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
        res.contains('second')
        res.contains('three')
        res.contains('1')
    }

    def "test read json value"() {
        given:
        def isin = 'RU000A1037L9'
        def res = getClass().getResource("/${isin}.json")

        when:
        def result = res.withInputStream {
            om.readValue(it, MoexIssInfo)
        }

        then:
        result.description.columns.size() == 7

        and:
        def d = result.description.data
        d.size() == 28
        d[4].getDataAt(2).get() == isin
        d[21].getDataAt(0).get() == 'COUPONPERCENT'
        d[-1].getDataAt(0).get() == 'EMITTER_ID'
    }
}
