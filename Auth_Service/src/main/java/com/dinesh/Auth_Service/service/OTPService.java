package com.dinesh.Auth_Service.service;

import com.dinesh.Auth_Service.exception.OtpException;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.Random;

@Service
public class OTPService {

    private final EmailService emailService;
    private final RedisService redisService;

    public OTPService(EmailService emailService,
                      RedisService redisService){
        this.emailService = emailService;
        this.redisService = redisService;
    }

    private final Random random = new SecureRandom();

    public void generateOtp(String email) {
        String otp = String.format("%06d", random.nextInt(1000000));
        String subject = "Your Auction Bidding System verification code is :";
        try {
            redisService.save(email, otp);
            emailService.sendEmail(email, subject, otp);
        } catch (Exception e) {
            throw new OtpException("Failed to send OTP");
        }
    }

    public boolean validateOtp(String key, String otp) {
        if(key == null || otp == null){
            return false;
        }
        if (!otp.equals(redisService.get(key))){
            return false;
        }
        redisService.delete(key);
        return true;
    }





}


