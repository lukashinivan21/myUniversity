package ru.schools.myuniversity.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {

    @Value("${server.port}")
    private Integer portNumber;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> getPortNumber() {
        return ResponseEntity.ok(portNumber);
    }
}
