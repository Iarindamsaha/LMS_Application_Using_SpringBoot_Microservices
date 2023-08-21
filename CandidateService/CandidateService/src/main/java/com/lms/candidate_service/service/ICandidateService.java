package com.lms.candidate_service.service;

import com.lms.candidate_service.entity.CandidateModel;
import com.lms.candidate_service.responses.Response;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

public interface ICandidateService {

    Response addCandidateDetails (CandidateModel candidateModel);
    Response searchByName (String name);
    Response searchByPhoneNumber (long phoneNumber);
    Response getAllCandidateByMarksDescOrder ();

}
