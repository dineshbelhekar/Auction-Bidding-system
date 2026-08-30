package com.dinesh.Auth_Service.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OTPRequestDTO {

    private String email;

    private String OTP;
}
