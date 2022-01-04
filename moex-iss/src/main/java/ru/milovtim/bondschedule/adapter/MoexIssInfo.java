package ru.milovtim.bondschedule.adapter;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Optional;

/**
 * Sample data is:
 * <pre>
 * {
 *      "description": {
 * 	        "columns": ["name", "title", "value", "type", "sort_order", "is_hidden", "precision"],
 * 	        "data": [
 *     		    ["SECID", "Код ценной бумаги", "RU000A1037L9", "string", 1, 0, null],
 *     		    ["NAME", "Полное наименование", "Бифорком Тек БО-П01", "string", 3, 0, null],
 *     		    ["SHORTNAME", "Краткое наименование", "БифТек1P1", "string", 4, 0, null]
 *     	    ]
 * }
 * </pre>
 */
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

        public Optional<String> getDataAt(int index) {
            if (index >= 0 && index < fieldDescriptions.length) {
                return Optional.ofNullable(fieldDescriptions[index]);
            }
            return Optional.empty();
        }
    }
}


