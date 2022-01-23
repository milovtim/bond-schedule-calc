package ru.milovtim.bondschedule.storage;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.NonNull;
import lombok.val;
import ru.milovtim.bondschedule.Bond;
import ru.milovtim.bondschedule.BondInfoStorage;
import ru.milovtim.bondschedule.ISIN;

import static ru.milovtim.bondschedule.storage.Constants.*;


public class YCBondInfoStorage implements BondInfoStorage {
    private final AmazonS3 amazonS3;
    private final String targetBucket;
    private final ObjectWriter bondWriter;

    public YCBondInfoStorage(String targetBucket) {
        this.targetBucket = targetBucket;
        this.amazonS3 = initS3();
        bondWriter = OBJECT_MAPPER.writerFor(Bond.class);
    }

    private AmazonS3 initS3() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSCredentialsProviderChain(
                        new EnvironmentVariableCredentialsProvider(),
                        new SystemPropertiesCredentialsProvider())
                )
                .withEndpointConfiguration(new EndpointConfiguration(YC_S3_ENDPOINT, YC_S3_REGION))
                .build();
    }

    @Override
    public void saveBondInfo(@NonNull Bond bond) {
        checkAccess();
//        ObjectMetadata objectMetadata = amazonS3.getObjectMetadata(targetBucket, bond.getIsin());
        try {
            val bondStr = bondWriter.writeValueAsString(bond);
            val putRes = amazonS3.putObject(targetBucket, bond.getIsin(), bondStr);
            System.out.println(putRes.getContentMd5());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkAccess() {
        amazonS3.headBucket(new HeadBucketRequest(targetBucket));
    }

    @Override
    public Bond loadBondInfo(ISIN isin) {
        return null;
    }
}
