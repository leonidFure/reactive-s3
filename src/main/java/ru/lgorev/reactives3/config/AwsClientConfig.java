package ru.lgorev.reactives3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.checksums.RequestChecksumCalculation;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Configuration;

import java.net.URI;
import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class AwsClientConfig {
    private final AwsClientProperties awsClientProperties;

    @Bean
    public S3AsyncClient s3AsyncClient(AwsCredentialsProvider awsCredentialsProvider) {
        return S3AsyncClient.builder()
                .httpClient(sdkAsyncHttpClient())
                .region(Region.EU_CENTRAL_1)
                .requestChecksumCalculation(RequestChecksumCalculation.WHEN_SUPPORTED)
                .credentialsProvider(awsCredentialsProvider)
                .endpointOverride(URI.create(awsClientProperties.endpoint()))
                .forcePathStyle(true)
                .serviceConfiguration(s3Configuration())
                .build();
    }

    private SdkAsyncHttpClient sdkAsyncHttpClient() {
        return NettyNioAsyncHttpClient.builder()
                .readTimeout(Duration.ofSeconds(30))
                .writeTimeout(Duration.ofSeconds(30))
                .maxConcurrency(64)
                .build();
    }

    private S3Configuration s3Configuration() {
        return S3Configuration.builder()
                .chunkedEncodingEnabled(true)
                .build();
    }

    @Bean
    AwsCredentialsProvider awsCredentialsProvider() {
        return () -> AwsBasicCredentials.create(
                awsClientProperties.accessKey(),
                awsClientProperties.secretKey());
    }
}
