package ru.milovtim.bondschedule;

public interface Bond {
    String getSecId();

    String getName();

    String getShortName();

    String getRegNumber();

    ISIN getIsin();

    java.time.LocalDate getIssueDate();

    java.time.LocalDate getMatDate();

    java.time.LocalDate getBuybackDate();

    Number getInitialFaceValue();

    String getFaceUnit();

    String getLatName();

    java.time.LocalDate getStartDateMoex();

    String getProgramRegistryNumber();

    Boolean getEarlyRepayment();

    Number getListLevel();

    Number getDaysToRedemption();

    Number getIssueSize();

    Number getFaceValue();

    Boolean getIsQualifiedInvestors();

    Number getCouponFrequency();

    java.time.LocalDate getCouponDate();

    Number getCouponPercent();

    Number getCouponValue();

    String getType();

    String getTypeName();

    String getGroup();

    String getGroupName();

    Number getEmitterId();
}
