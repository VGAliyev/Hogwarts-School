package ru.hogwarts.school.service.Impl;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoServiceImpl implements InfoService {
    @Override
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

        return String.format("Sum original (%d): %d ms\nSum modified (%d): %d ms",
                sumOriginal, sumOriginalMs, sumModified, sumModifiedMs);
    }
}
