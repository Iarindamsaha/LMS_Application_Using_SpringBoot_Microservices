package com.lms.admin_service.controller;

import com.lms.admin_service.dto.LoginDTO;
import com.lms.admin_service.external_model.CandidateModel;
import com.lms.admin_service.responses.Response;
import com.lms.admin_service.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    IAdminService adminService;


    @PostMapping("/adminLogin")
    public ResponseEntity<Response> login (@RequestBody LoginDTO loginDTO){

        return new ResponseEntity<>(adminService.adminLogin(loginDTO),HttpStatus.OK);

    }


    @PostMapping("/addCandidates")
    public ResponseEntity<Response> addCandidates (@RequestBody CandidateModel candidateModel, @RequestHeader String token){

        return new ResponseEntity<>(adminService.addCandidates(candidateModel,token), HttpStatus.OK);

    }

    @GetMapping("/searchCandidatesByName")
    public ResponseEntity<Response> getAllCandidatesByName (@RequestParam String name, @RequestHeader String token){

        return new ResponseEntity<>(adminService.searchCandidateByName(name,token), HttpStatus.OK);

    }

    @GetMapping("/searchByPhoneNumber")
    public ResponseEntity<Response> getCandidateDetailsByPhoneNumber
            (@RequestParam long phoneNumber, @RequestHeader String token){

        return new ResponseEntity<>(adminService.searchCandidateByPhoneNumber(phoneNumber,token),HttpStatus.OK);

    }

    @GetMapping("/getAllCandidateByMarksHighToLow")
    public ResponseEntity<Response> getAllCandidateByMarksHighToLow (@RequestHeader String token){

        return  new ResponseEntity<>(adminService.getAllStudentDetailsByMarksHighToLow(token),HttpStatus.OK);

    }




}
