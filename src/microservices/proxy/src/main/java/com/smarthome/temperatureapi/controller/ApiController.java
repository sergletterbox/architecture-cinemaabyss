package com.smarthome.temperatureapi.controller;

import com.smarthome.temperatureapi.RequestLoggingFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    private final RestTemplate restTemplate = new RestTemplate();;

    @Value("${movies.migration.percent}")
    private int migrationPercent;

    @Value("${monolith.url}")
    private String monolithUrl;

    @Value("${movies.service.url}")
    private String moviesUrl;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        logger.info("migrationPercent={}", migrationPercent);
        logger.info("monolithUrl={}", monolithUrl);
        logger.info("moviesUrl={}", moviesUrl);
    }

    @GetMapping("/users")
    public String getUsers(HttpServletResponse response) throws IOException {
        String result = restTemplate.getForObject(monolithUrl + "/api/users", String.class);
        logger.info("result = {}", result);
        return result;
    }

    @GetMapping("/movies")
    public String getMovies(HttpServletResponse response) throws IOException {
        double probability = migrationPercent / 100.0;
        double current = Math.random();
        String url = current < probability ? moviesUrl : monolithUrl;
        url += "/api/movies";
        logger.info("url={} current={}", url, current);
        String result = restTemplate.getForObject(url, String.class);
        logger.info("result = {}", result);
        return result;
    }
}