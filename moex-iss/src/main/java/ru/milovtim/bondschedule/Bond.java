package ru.milovtim.bondschedule;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder()
public class Bond {
    private String secId;
    private String name;
    private String shortName;
    private String regNumber;
    private ISIN isin;
    private LocalDate issueDate;
    private LocalDate matDate;
    private LocalDate buybackDate;
    private Number initialFaceValue;
    private String faceUnit;
    private String latName;
    private LocalDate startDateMoex;
    private String programRegistryNumber;
    private Boolean earlyRepayment;
    private Number listLevel;
    private Number daysToRedemption;
    private Number issueSize;
    private Number faceValue;
    private Boolean isQualifiedInvestors;
    private Number couponFrequency;
    private LocalDate couponDate;
    private Number couponPercent;
    private Number couponValue;
    private String type;
    private String typeName;
    private String group;
    private String groupName;
    private Number emitterId;
}
