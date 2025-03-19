package ru.lgorev.reactives3.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.lgorev.reactives3.aws.AwsClient;

import java.nio.ByteBuffer;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class AwsController {
    private final AwsClient awsClient;

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Flux<ByteBuffer> download(@RequestParam(name = "key") String key) {
        return awsClient.download(key);
    }
}
