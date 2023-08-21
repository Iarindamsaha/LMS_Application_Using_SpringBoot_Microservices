package com.lms.admin_service.service;

import com.lms.admin_service.dto.LoginDTO;
import com.lms.admin_service.exceptions.UserException;
import com.lms.admin_service.external_model.CandidateModel;
import com.lms.admin_service.external_services.CandidateService;
import com.lms.admin_service.external_services.UserService;
import com.lms.admin_service.responses.Response;
import com.lms.admin_service.responses.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AdminService implements IAdminService{

    @Autowired
    CandidateService candidateService;
    @Autowired
    UserService userService;
    @Autowired
    Response response;
    @Autowired
    RestTemplate restTemplate;

    @Override
    public Response adminLogin(LoginDTO loginDTO) {

        Response response = userService.externalLogin(loginDTO);;
        if(response.getMessage().equalsIgnoreCase("User Not available In Our Database")){
            throw new UserException("User not available in the database", HttpStatus.BAD_REQUEST);
        } else if (response.getMessage().equalsIgnoreCase("User Is Available || Password Incorrect")) {
            throw new UserException("Password is incorrect || Please Check The Password",HttpStatus.BAD_REQUEST);
        }
        else {
            return response;
        }

    }

    @Override
    public Response addCandidates(CandidateModel candidateModel, String token) {

        try{

            HttpHeaders tokenPass = new HttpHeaders();
            tokenPass.add("token",token);
            HttpEntity<Void> reqEntity = new HttpEntity<>(tokenPass);

            ResponseEntity<UserRole> userRoleResponseEntity = restTemplate.exchange
                    ("http://USER-SERVICE/user/getUserRole", HttpMethod.GET,reqEntity, UserRole.class);

            if(userRoleResponseEntity.getBody() == null){
                throw new UserException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(userRoleResponseEntity.getBody().getRole().equalsIgnoreCase("ADMIN")){
                return candidateService.addCandidateDetails(candidateModel);
            }
            else {
                response.setMessage("User is Not an Admin");
                response.setObject("Access Denied");
                return response;
            }

        }
        catch (HttpServerErrorException e){
            throw new UserException("Token Is Invalid Or Expired",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Response searchCandidateByName(String name, String token) {

        try{

            HttpHeaders tokenPass = new HttpHeaders();
            tokenPass.add("token",token);
            HttpEntity<Void> reqEntity = new HttpEntity<>(tokenPass);

            ResponseEntity<UserRole> userRoleResponseEntity = restTemplate.exchange
                    ("http://USER-SERVICE/user/getUserRole", HttpMethod.GET,reqEntity, UserRole.class);

            if(userRoleResponseEntity.getBody() == null){
                throw new UserException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(userRoleResponseEntity.getBody().getRole().equalsIgnoreCase("ADMIN")){
                return candidateService.searchByName(name);
            }
            else {
                response.setMessage("User is Not an Admin");
                response.setObject("Access Denied");
                return response;
            }

        }
        catch (HttpServerErrorException e){
            throw new UserException("Token Is Invalid Or Expired",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Response searchCandidateByPhoneNumber(long phoneNumber, String token) {
        try{

            HttpHeaders tokenPass = new HttpHeaders();
            tokenPass.add("token",token);
            HttpEntity<Void> reqEntity = new HttpEntity<>(tokenPass);

            ResponseEntity<UserRole> userRoleResponseEntity = restTemplate.exchange
                    ("http://USER-SERVICE/user/getUserRole", HttpMethod.GET,reqEntity, UserRole.class);

            if(userRoleResponseEntity.getBody() == null){
                throw new UserException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(userRoleResponseEntity.getBody().getRole().equalsIgnoreCase("ADMIN")){
                return candidateService.searchByPhoneNumber(phoneNumber);
            }
            else {
                response.setMessage("User is Not an Admin");
                response.setObject("Access Denied");
                return response;
            }

        }
        catch (HttpServerErrorException e){
            throw new UserException("Token Is Invalid Or Expired",HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Response getAllStudentDetailsByMarksHighToLow(String token) {
        try{

            HttpHeaders tokenPass = new HttpHeaders();
            tokenPass.add("token",token);
            HttpEntity<Void> reqEntity = new HttpEntity<>(tokenPass);

            ResponseEntity<UserRole> userRoleResponseEntity = restTemplate.exchange
                    ("http://USER-SERVICE/user/getUserRole", HttpMethod.GET,reqEntity, UserRole.class);

            if(userRoleResponseEntity.getBody() == null){
                throw new UserException("Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if(userRoleResponseEntity.getBody().getRole().equalsIgnoreCase("ADMIN")){
                return candidateService.getAllCandidateByMarksInDescOrder();
            }
            else {
                response.setMessage("User is Not an Admin");
                response.setObject("Access Denied");
                return response;
            }

        }
        catch (HttpServerErrorException e){
            throw new UserException("Token Is Invalid Or Expired",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
