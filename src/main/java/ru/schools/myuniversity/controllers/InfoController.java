package ru.schools.myuniversity.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

@RestController
public class InfoController {

    @Value("${server.port}")
    private Integer portNumber;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPortNumber() {
        return ResponseEntity.ok(portNumber);
    }

    @GetMapping("/getSum")
    public int resultSum() {
        return Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000L)
                .reduce(0, Integer::sum);
    }
}
