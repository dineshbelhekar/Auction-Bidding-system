package com.dinesh.Auth_Service.controller;

import com.dinesh.Auth_Service.Entity.User;
import com.dinesh.Auth_Service.request.EmailRequestDTO;
import com.dinesh.Auth_Service.request.LoginRequestDTO;
import com.dinesh.Auth_Service.request.OTPRequestDTO;
import com.dinesh.Auth_Service.service.AuthService;
import com.dinesh.Auth_Service.service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final OTPService otpService;

    public AuthController(AuthService authService,
                          OTPService otpService){
        this.authService = authService;
        this.otpService = otpService;
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

    @PostMapping("/getotp")
    public ResponseEntity<HttpStatus> getOTP(@RequestBody EmailRequestDTO emailRequestDTO){
        otpService.generateOtp(emailRequestDTO.getEmail());
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestBody OTPRequestDTO otpRequestDTO) {
        return otpService.validateOtp(otpRequestDTO.getEmail(), otpRequestDTO.getOTP()) ? "OTP Verified Successfully" : "Invalid OTP";
    }
}
