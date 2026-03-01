package dev.vladis.TuneWaveApp.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @PostMapping("/login")
    public String login() {
        return "This is login API";
    }

    @PostMapping("/register")
    public String register() {
        return "This is Register API";
    }
}
