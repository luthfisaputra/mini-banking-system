package com.project.minibankingsystem.controller;

import com.project.minibankingsystem.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // untuk demo: hardcode user
        if ("luthfi".equals(username) && "1234".equals(password)) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Username atau password salah");
        }
    }
}
