package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@RestController
@RequestMapping("info/")
public class InfoController {
    @Value("${server.port}")
    Integer port;

    @GetMapping("port")
    public Integer getPort() {
        return port;
    }

    @GetMapping("sum")
    public String getSum() {
        long start = System.currentTimeMillis();

        int sumOriginal = Stream
                .iterate(1, a -> a + 1)
                .limit(10_000_000)
                .reduce(0, Integer::sum);

        long sumOriginalMs = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();

        int sumModified = IntStream
                .range(0, 10_000_000)
                .parallel()
                .map(a -> a + 1)
                .reduce(0, Integer::sum);


        long sumModifiedMs = System.currentTimeMillis() - start;

        return String.format("Sum original (%d): %d ms\nSum modified (%d): %d ms", sumOriginal, sumOriginalMs, sumModified, sumModifiedMs);
    }
}
