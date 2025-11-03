package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/keycloak")
public class KeycloakController {
    private static final Logger log = LoggerFactory.getLogger(KeycloakController.class);
    
    private final RestTemplate restTemplate;
    
    @Value("${keycloak.url}")
    private String keycloakUrl;

    // Use constructor injection
    public KeycloakController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/ping")
    public ResponseEntity<String> pingKeycloak() {
        try {
            log.info("Attempting to call Keycloak at: {}", keycloakUrl);
            String response = restTemplate.getForObject(keycloakUrl, String.class);
            log.info("Received response from Keycloak");
            return ResponseEntity.ok("Keycloak response: " + response);
        } catch (Exception e) {
            log.error("Keycloak call failed", e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}