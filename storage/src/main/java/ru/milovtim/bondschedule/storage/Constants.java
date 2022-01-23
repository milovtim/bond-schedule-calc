package ru.milovtim.bondschedule.storage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Constants {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String  YC_S3_ENDPOINT = "storage.yandexcloud.net";
    public static final String  YC_S3_REGION = "ru-central1";
}
