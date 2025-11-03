package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@SpringBootApplication
@RestController
@RequestMapping("/keycloak")
public class DemoApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Value("${keycloak.url}")
    private String keycloakUrl;

    private final RestTemplate restTemplate;

    // Use constructor injection
    public DemoApplication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Application started with Keycloak URL: {}", keycloakUrl);
    }

    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException {
        // Create trust manager that trusts all certificates (for demo only)
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        
        // Install the all-trusting trust manager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        
        // Create custom RestTemplate that trusts all certificates
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory(sslContext));
        
        return restTemplate;
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

    // Custom SSL Request Factory
    private static class SimpleClientHttpRequestFactory 
        extends org.springframework.http.client.SimpleClientHttpRequestFactory {
        
        private final SSLContext sslContext;

        public SimpleClientHttpRequestFactory(SSLContext sslContext) {
            this.sslContext = sslContext;
        }

        @Override
        protected void prepareConnection(java.net.HttpURLConnection connection, 
                                        String httpMethod) throws IOException {
            if (connection instanceof javax.net.ssl.HttpsURLConnection) {
                javax.net.ssl.HttpsURLConnection httpsConnection = 
                    (javax.net.ssl.HttpsURLConnection) connection;
                httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());
                httpsConnection.setHostnameVerifier((hostname, session) -> true);
            }
            super.prepareConnection(connection, httpMethod);
        }
    }
}