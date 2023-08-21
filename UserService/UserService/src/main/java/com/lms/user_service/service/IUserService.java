package com.lms.user_service.service;

import com.lms.user_service.dto.LoginDto;
import com.lms.user_service.dto.OtpCheckingDTO;
import com.lms.user_service.dto.UserDto;
import com.lms.user_service.dto.VerificationDto;
import com.lms.user_service.responses.Response;
import com.lms.user_service.responses.UserResponse;
import com.lms.user_service.responses.UserRole;

public interface IUserService {

    Response registerUser(UserDto userDto);
    Response userLogin (LoginDto loginDto);
    Response externalLogin (LoginDto loginDto);
    Response verifyUser (String token);
    Response verifyUserSendOtp (String email);
    Response verifyUserUsingOtp (VerificationDto verificationDto);
    UserResponse getUserIfAvailable (String email);
    Response getOtpForgetPassword (String email);
    Response verifyOtpForForgetPassword (OtpCheckingDTO otpCheckingDTO);
    Response changePassword (String password, String token);
    UserRole getUserRole (String token);

}
