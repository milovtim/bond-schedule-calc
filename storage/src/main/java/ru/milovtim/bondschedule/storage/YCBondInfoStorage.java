package ru.milovtim.bondschedule.storage;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.val;
import ru.milovtim.bondschedule.Bond;
import ru.milovtim.bondschedule.BondInfoStorage;
import ru.milovtim.bondschedule.ISIN;


public class YCBondInfoStorage implements BondInfoStorage {

    @Override
    public void saveBondInfo(Bond bond) {
        AWSCredentials awsCreds = new BasicAWSCredentials("hello", "world");
        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "storage.yandexcloud.net", "ru-central1"
                        )
                )
                .build();
        s3.listObjects("some");

    }

    @Override
    public Bond loadBondInfo(ISIN isin) {
        return null;
    }
}
