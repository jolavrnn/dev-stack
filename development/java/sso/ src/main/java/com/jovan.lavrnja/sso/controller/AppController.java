package com.example.sso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser user) {
        return "Welcome, " + user.getFullName() + "!";
    }

    @GetMapping("/admin")
    public String adminArea() {
        return "Admin Dashboard";
    }

    @GetMapping("/user")
    public String userArea() {
        return "User Dashboard";
    }

    @GetMapping("/unsecured")
    public String publicPage() {
        return "Public Content - No Auth Required";
    }

    @GetMapping("/user-info")
    public Object getUserInfo(@AuthenticationPrincipal OidcUser user) {
        return user.getIdToken().getClaims();
    }
}