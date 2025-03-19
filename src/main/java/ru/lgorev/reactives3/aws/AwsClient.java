package ru.lgorev.reactives3.aws;

import reactor.core.publisher.Flux;

import java.nio.ByteBuffer;

public interface AwsClient {
    Flux<ByteBuffer> download(String fileLink);
}
