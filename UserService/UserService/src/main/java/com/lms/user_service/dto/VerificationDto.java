package com.lms.user_service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class VerificationDto {

    private String email;
    private int otp;

}
