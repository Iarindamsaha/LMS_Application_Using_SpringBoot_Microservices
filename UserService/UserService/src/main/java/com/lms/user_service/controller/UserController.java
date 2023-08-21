package com.lms.user_service.controller;

import com.lms.user_service.dto.LoginDto;
import com.lms.user_service.dto.OtpCheckingDTO;
import com.lms.user_service.dto.UserDto;
import com.lms.user_service.dto.VerificationDto;
import com.lms.user_service.responses.Response;
import com.lms.user_service.responses.UserResponse;
import com.lms.user_service.responses.UserRole;
import com.lms.user_service.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;


    @PostMapping("/register")
    public ResponseEntity<Response> registerUser (@RequestBody UserDto userDto){

        return new ResponseEntity<>(userService.registerUser(userDto), HttpStatus.ACCEPTED);

    }

    @PostMapping("/login")
    public ResponseEntity<Response> userLogin(@RequestBody LoginDto loginDto){

        return new ResponseEntity<>(userService.userLogin(loginDto),HttpStatus.OK);

    }

    @PostMapping("/externalLogin")
    public ResponseEntity<Response> externalLogin(@RequestBody LoginDto loginDto){

        return new ResponseEntity<>(userService.externalLogin(loginDto),HttpStatus.OK);

    }


    @GetMapping("/verify")
    public ResponseEntity<Response> userVerification(@RequestHeader String token){

        return new ResponseEntity<>(userService.verifyUser(token),HttpStatus.OK);

    }

    @GetMapping("/sendOtpForVerification")
    public ResponseEntity<Response> userVerificationSendOtp (@RequestParam String email){

        return new ResponseEntity<>(userService.verifyUserSendOtp(email),HttpStatus.OK);

    }

    @GetMapping("/verifyUsingOtp")
    public ResponseEntity<Response> userVerificationUsingOtp (@RequestBody VerificationDto verificationDto){

        return new ResponseEntity<>(userService.verifyUserUsingOtp(verificationDto),HttpStatus.OK);

    }

    @GetMapping("/getUserResponse")
    public ResponseEntity<UserResponse> getUserResponseIfUserIsAvailable (@RequestParam String email){

        return new ResponseEntity<>(userService.getUserIfAvailable(email),HttpStatus.OK);

    }

    @GetMapping("/forgetPasswordGetOtp")
    public ResponseEntity<Response> getOtpForForgetPassword (@RequestParam String email){

        return new ResponseEntity<>(userService.getOtpForgetPassword(email),HttpStatus.OK);

    }

    @GetMapping("/verifyOtpForForgetPassword")
    public ResponseEntity<Response> verifyOtpInForgetPassword(@RequestBody OtpCheckingDTO otpCheckingDTO){

        return new ResponseEntity<>(userService.verifyOtpForForgetPassword(otpCheckingDTO),HttpStatus.OK);

    }

    @PostMapping("/changePassword")
    public ResponseEntity<Response> changePassword(@RequestParam String password, @RequestHeader String token){

        return new ResponseEntity<>(userService.changePassword(password,token), HttpStatus.OK);

    }

    @GetMapping("/getUserRole")
    public ResponseEntity<UserRole> getUserRole (@RequestHeader String token){

        return new ResponseEntity<>(userService.getUserRole(token),HttpStatus.OK);

    }





}
