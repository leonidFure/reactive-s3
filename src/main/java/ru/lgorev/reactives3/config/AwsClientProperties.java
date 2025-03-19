package ru.lgorev.reactives3.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws")
public record AwsClientProperties(
        String accessKey,
        String secretKey,
        String bucketName,
        String endpoint,
        long readTimeout,
        long writeTimeout
) {
}
