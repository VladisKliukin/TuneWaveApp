package dev.vladis.TuneWaveApp.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/health")
@AllArgsConstructor
public class RootController {
    @GetMapping
    public String healthCheck() {
        log.info("Health Check");
        return "Api Working";
    }
}
