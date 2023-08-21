package com.lms.candidate_service.controller;

import com.lms.candidate_service.entity.CandidateModel;
import com.lms.candidate_service.responses.Response;
import com.lms.candidate_service.service.ICandidateService;
import org.apache.http.client.protocol.ResponseProcessCookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    ICandidateService candidateService;

    @PostMapping("/addCandidateDetails")
    public ResponseEntity<Response> addCandidateDetails(@RequestBody CandidateModel candidateModel){

        return new ResponseEntity<>(candidateService.addCandidateDetails(candidateModel), HttpStatus.OK);

    }

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<Response> searchByName(@PathVariable String name){

        return new ResponseEntity<>(candidateService.searchByName(name),HttpStatus.OK);

    }

    @GetMapping("/searchByPhoneNumber/{phoneNumber}")
    public ResponseEntity<Response> searchByPhoneNumber (@PathVariable long phoneNumber){

        return new ResponseEntity<>(candidateService.searchByPhoneNumber(phoneNumber),HttpStatus.OK);

    }

    @GetMapping("/getAllByMarksDescOrder")
    public ResponseEntity<Response> getAllCandidateByMarksInDescOrder (){

        return new ResponseEntity<>(candidateService.getAllCandidateByMarksDescOrder(),HttpStatus.OK);

    }




}
