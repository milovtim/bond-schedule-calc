package ru.milovtim.bondschedule.storage

import groovy.transform.Canonical
import ru.milovtim.bondschedule.Bond

import java.time.LocalDate

@Canonical
class BondPOJO implements Bond {
    String secId
    String name
    String shortName
    String regNumber
    String isin
    LocalDate issueDate
    LocalDate matDate
    LocalDate buybackDate
    Number initialFaceValue
    String faceUnit
    String latName
    LocalDate startDateMoex
    String programRegistryNumber
    Boolean earlyRepayment
    Number listLevel
    Number daysToRedemption
    Number issueSize
    Number faceValue
    Boolean isQualifiedInvestors
    Number couponFrequency
    LocalDate couponDate
    Number couponPercent
    Number couponValue
    String type
    String typeName
    String group
    String groupName
    Number emitterId
}
