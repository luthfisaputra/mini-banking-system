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
    public String login(@RequestBody LoginRequest request) {
        // untuk demo: hardcode user
        if ("luthfi".equals(request.getUsername()) && "1234".equals(request.getPassword())) {
            return jwtUtil.generateToken(request.getUsername());
        } else {
            throw new RuntimeException("Username atau password salah");
        }
    }
}
