package com.lms.admin_service.service;

import com.lms.admin_service.dto.LoginDTO;
import com.lms.admin_service.external_model.CandidateModel;
import com.lms.admin_service.responses.Response;

public interface IAdminService {

    Response adminLogin (LoginDTO loginDTO);
    Response addCandidates (CandidateModel candidateModel, String token);
    Response searchCandidateByName (String name, String token);
    Response searchCandidateByPhoneNumber (long phoneNumber, String token);
    Response getAllStudentDetailsByMarksHighToLow (String token);

}
