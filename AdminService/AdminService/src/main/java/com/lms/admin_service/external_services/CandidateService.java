package com.lms.admin_service.external_services;

import com.lms.admin_service.external_model.CandidateModel;
import com.lms.admin_service.responses.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "CANDIDATE-SERVICE")
public interface CandidateService {

    @PostMapping("/candidate/addCandidateDetails")
    public Response addCandidateDetails( CandidateModel candidateModel);

    @GetMapping("/candidate/searchByName/{name}")
    Response searchByName (@PathVariable String name);

    @GetMapping("/candidate/searchByPhoneNumber/{phoneNumber}")
    Response searchByPhoneNumber (@PathVariable long phoneNumber);

    @GetMapping("/candidate/getAllByMarksDescOrder")
    public Response getAllCandidateByMarksInDescOrder ();
}
