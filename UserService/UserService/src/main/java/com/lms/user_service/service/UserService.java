package com.lms.user_service.service;


import com.lms.user_service.dto.*;
import com.lms.user_service.entity.UserEntity;
import com.lms.user_service.exceptions.UserException;
import com.lms.user_service.jwt_config.JwtUtility;
import com.lms.user_service.repository.UserRepo;
import com.lms.user_service.responses.Response;
import com.lms.user_service.responses.UserResponse;
import com.lms.user_service.responses.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    Response response;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    JwtUtility jwtUtility;

    @Value("${rabbit.exchange.name}")
    String topicExchange;
    @Value("${rabbit.routing.name}")
    String routingKey;
    private final RabbitTemplate rabbitTemplate;

    public UserService (RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Response registerUser(UserDto userDto) {

        UserEntity checkUser = userRepo.findByEmail(userDto.getEmail());

        if(checkUser != null){
            response.setMessage("Email Id Already Exists");
            throw new UserException("Email Id Already Registered To One User", HttpStatus.INTERNAL_SERVER_ERROR);

        }
        else {

            UserEntity newUser = modelMapper.map(userDto, UserEntity.class);
            if(userDto.getEmail().equalsIgnoreCase("asaha15071998@gmail.com")){
                newUser.setRole("ADMIN");
            }else {
                newUser.setRole("USER");
            }
            String randomUserId = UUID.randomUUID().toString();
            newUser.setId(randomUserId);
            userRepo.save(newUser);
            response.setMessage("User Registered Successfully");
            response.setObject("Hey " + userDto.getFirstname()
                    + " " +userDto.getLastname() + " ,"
                    + " Welcome To Our Lms Portal...");

            String subject = "User Registered Successfully";
            String body = "Welcome To Our LMS Portal "
                    + "\nYou Can Find Your Candidate Profile in Our Portal";
            String emailId = newUser.getEmail();

            EmailSendingDTO emailSendingDTO = new EmailSendingDTO(emailId,subject,body);
            rabbitTemplate.convertAndSend(topicExchange,routingKey,emailSendingDTO);
            return response;

        }

    }

    @Override
    public Response userLogin(LoginDto loginDto) {

        UserEntity checkUser = userRepo.findByEmail(loginDto.getEmail());
        UserEntity checkUserPassword = userRepo
                .findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
        if(checkUser == null){
            throw new UserException("User Is Not Registered To Our System",HttpStatus.INTERNAL_SERVER_ERROR);
        } else if (checkUserPassword == null) {
            throw new UserException("User is Registered || Password is Incorrect",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            String token = jwtUtility.generateToken(loginDto);
            response.setMessage("User Logged In Successfully");
            response.setObject("Token = "+token);
            return response;
        }


    }

    @Override
    public Response externalLogin(LoginDto loginDto) {

        UserEntity checkUser = userRepo.findByEmail(loginDto.getEmail());
        UserEntity checkUserPassword = userRepo
                .findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
        if(checkUser == null){
            response.setMessage("User Not available In Our Database");
            response.setObject(null);
            return response;
        } else if (checkUserPassword == null) {
            response.setMessage("User Is Available || Password Incorrect");
            response.setObject(null);
            return response;
        }
        else {
            String token = jwtUtility.generateToken(loginDto);
            response.setMessage("User Logged In Successfully");
            response.setObject("Token = "+token);
            return response;
        }
    }

    @Override
    public Response verifyUser(String token ) {

        if(jwtUtility.validateTokenForVerify(token)){
            LoginDto loginDto = jwtUtility.decodeToken(token);
            UserEntity checkUser = userRepo
                    .findByEmailAndPassword(loginDto.getEmail(),loginDto.getPassword());
            response.setMessage("User Verified Successfully");
            String welcomeMessage = "Your Account is Successfully Verified, You can Proceed Now...";
            response.setObject("Hey " +
                    checkUser.getFirstname() + " " + checkUser.getLastname() + " , "
                    +welcomeMessage);
            return response;
        }
        else {
            throw new UserException("Session Expired || " +
                    "Please Enter Your Email In The Next Page" ,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Response verifyUserSendOtp(String email) {

        UserEntity checkUser = userRepo.findByEmail(email);
        if(checkUser == null){
            throw new UserException
                    ("User Doesn't Exists || Please Check Your Email",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            Random random = new Random();
            int generateOtp = random.nextInt(90000);

            checkUser.setOtp(generateOtp);
            userRepo.save(checkUser);

            response.setMessage("Hey " + checkUser.getFirstname() + " " + checkUser.getLastname());
            response.setObject("Otp Has Been Successfully Sent To Your Email Id");

            String subject = "Verify Your Account";
            String body = "To Verify your Account PLease Enter This Otp : " + generateOtp;
            String toEmail = checkUser.getEmail();

            EmailSendingDTO emailSendingDTO =
                    new EmailSendingDTO(toEmail,subject,body);

            rabbitTemplate.convertAndSend(topicExchange,routingKey,emailSendingDTO);
            return response;

        }
    }

    @Override
    public Response verifyUserUsingOtp(VerificationDto verificationDto) {

        UserEntity checkUser = userRepo.findByEmail(verificationDto.getEmail());
        UserEntity checkUserByOtp = userRepo
                .findByEmailAndOtp(verificationDto.getEmail(), verificationDto.getOtp());
        if(checkUser == null){
            throw new UserException("User Email Not Available In The Database",HttpStatus.BAD_REQUEST);
        } else if (checkUserByOtp == null) {
            throw new UserException("Otp is Incorrect || Please Check The Otp", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            response.setMessage("Hey " + checkUserByOtp.getFirstname() + " " + checkUserByOtp.getLastname());
            response.setObject("Your Account is Successfully Verified, You can Proceed Now...");
            checkUserByOtp.setOtp(0);
            userRepo.save(checkUserByOtp);
            return response;
        }


    }

    @Override
    public UserResponse getUserIfAvailable(String email) {

        UserEntity checkUser = userRepo.findByEmail(email);
        if(checkUser == null){
            return null;
        }
        else {
            return modelMapper.map(checkUser, UserResponse.class);
        }
    }

    @Override
    public Response getOtpForgetPassword(String email) {

        UserEntity checkUser = userRepo.findByEmail(email);
        if(checkUser == null){
            throw new UserException("User Not Available In Out Database" , HttpStatus.BAD_REQUEST);
        }
        else {

            Random random = new Random();
            int generateOtp = random.nextInt(90000);

            checkUser.setOtp(generateOtp);
            userRepo.save(checkUser);

            String subject = "OTP To Recover Password";
            String body = "Your otp for setting New Password is : " + generateOtp;

            EmailSendingDTO emailSendingDTO =
                    new EmailSendingDTO(email,subject,body);
            rabbitTemplate.convertAndSend(topicExchange,routingKey,emailSendingDTO);

            response.setMessage("Otp Sent To You Email Please Check Email");
            return response;

        }


    }

    @Override
    public Response verifyOtpForForgetPassword(OtpCheckingDTO otpCheckingDTO) {

        UserEntity checkUser = userRepo.findByEmail(otpCheckingDTO.getEmail());
        UserEntity checkUserByEmailAndOtp =
                userRepo.findByEmailAndOtp(otpCheckingDTO.getEmail(),otpCheckingDTO.getOtp());

        if(checkUser == null){
            throw new UserException("User Not Registered To Our Database ||" +
                    "Please check Your Email",HttpStatus.BAD_REQUEST);
        } else if (checkUserByEmailAndOtp == null) {
            throw new UserException("Otp Is Incorrect" , HttpStatus.BAD_REQUEST);
        } else {

            String token = jwtUtility.generateTokenUsingOtp(otpCheckingDTO);
            response.setMessage("Click On link To Create New Password");
            response.setObject("Token : " + " " + token);
            return response;

        }

    }

    @Override
    public Response changePassword(String password, String token) {

        OtpCheckingDTO otpCheckingDTO = jwtUtility.decodeTokenInOtp(token);
        UserEntity checkUser = userRepo
                .findByEmailAndOtp(otpCheckingDTO.getEmail(),otpCheckingDTO.getOtp());
        if(checkUser == null){
            throw new UserException("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {

            checkUser.setPassword(password);
            checkUser.setOtp(0);
            userRepo.save(checkUser);
            response.setMessage("Your Password Has Been Changed Successfully");
            response.setObject(null);
            return response;

        }
    }

    @Override
    public UserRole getUserRole(String token) {

        LoginDto loginDto = jwtUtility.decodeToken(token);
        UserEntity checkUser = userRepo.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if(checkUser == null){
            throw new UserException("Something Went Wrong",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return modelMapper.map(checkUser, UserRole.class);
        }
    }
}
