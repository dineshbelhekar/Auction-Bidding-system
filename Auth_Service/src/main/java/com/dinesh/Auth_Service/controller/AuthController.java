package com.dinesh.Auth_Service.controller;

import com.dinesh.Auth_Service.Entity.User;
import com.dinesh.Auth_Service.request.LoginRequestDTO;
import com.dinesh.Auth_Service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/greetings")
    public String greetings(){
        return "hello world";
    }

    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> signUp(@RequestBody User user){
        authService.saveNewUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody LoginRequestDTO loginRequestDTO) {
        String token = authService.login(loginRequestDTO);
        return ResponseEntity.ok(token);
    }

}
