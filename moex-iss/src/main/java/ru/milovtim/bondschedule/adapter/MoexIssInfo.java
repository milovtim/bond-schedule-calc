package ru.milovtim.bondschedule.adapter;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

@lombok.Data
public class MoexIssInfo {

    private Description description;

    @lombok.Data
    public static class Description {
        private List<String> columns;
        private List<FieldData> data;
    }



    @lombok.Data
    public static class FieldData {
        @JsonValue
        private String[] fieldDescriptions;

        @JsonCreator
        public FieldData(String[] fieldDescriptions) {
            this.fieldDescriptions = fieldDescriptions;
        }
    }
}


