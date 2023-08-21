package com.lms.user_service.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserDto {

    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
