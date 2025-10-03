package com.nova.team.utp_connect_backend.controllers;

import com.nova.team.utp_connect_backend.enities.User;
import com.nova.team.utp_connect_backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        return authService.login(email, password);
    }

    @GetMapping("/current-user")
    public User currentUser(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        return authService.currentUser(token);
    }
}