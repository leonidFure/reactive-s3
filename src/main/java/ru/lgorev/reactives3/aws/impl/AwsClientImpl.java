package ru.lgorev.reactives3.aws.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lgorev.reactives3.aws.AwsClient;
import ru.lgorev.reactives3.config.AwsClientConfig;
import ru.lgorev.reactives3.config.AwsClientProperties;
import software.amazon.awssdk.core.async.AsyncResponseTransformer;
import software.amazon.awssdk.core.async.listener.SubscriberListener;
import software.amazon.awssdk.core.internal.async.PublisherAsyncResponseTransformer;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;

import java.awt.image.DataBuffer;
import java.nio.ByteBuffer;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AwsClientImpl implements AwsClient {
    private final S3AsyncClient s3AsyncClient;
    private final AwsClientProperties awsClientProperties;

    @Override
    public Flux<ByteBuffer> download(String fileKey) {
        final var request = GetObjectRequest.builder()
                .bucket(awsClientProperties.bucketName())
                .key(fileKey)
                .build();
        return Mono.fromFuture(s3AsyncClient.getObject(request, AsyncResponseTransformer.toPublisher()))
                .log()
                .flatMapMany(Flux::from)
                .log();
    }
}
