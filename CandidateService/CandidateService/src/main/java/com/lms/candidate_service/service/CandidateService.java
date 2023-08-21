package com.lms.candidate_service.service;

import com.lms.candidate_service.entity.CandidateModel;
import com.lms.candidate_service.repository.CandidateRepository;
import com.lms.candidate_service.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CandidateService implements ICandidateService {

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    Response response;


    @Override
    public Response addCandidateDetails(CandidateModel candidateModel) {

        String randomId = UUID.randomUUID().toString();
        candidateModel.setId(randomId);
        candidateRepository.save(candidateModel);
        response.setMessage("Candidate Added Successfully");
        response.setObject(candidateModel);
        return response;

    }

    @Override
    public Response searchByName(String name) {

        try {
            String[] names = name.split("\\s+");
            List<CandidateModel> allCandidates =
                    candidateRepository.findAllByFirstnameAndLastname(names[0],names[1]);
            if(allCandidates.size() > 0){
                response.setMessage("All Candidate Details Are :");
                response.setObject(allCandidates);
            }
            else {
                response.setMessage("No Candidate Found");
                response.setObject(null);

            }
            return response;
        }catch (ArrayIndexOutOfBoundsException e){

            List<CandidateModel> candidateDetails = candidateRepository.findAllByFirstname(name);
            System.out.println(candidateDetails);
            if(candidateDetails.size() > 0){
                response.setMessage("All Candidate Details Are :");
                response.setObject(candidateDetails);
            }
            else {
                response.setMessage("No Candidate Found");
                response.setObject(null);
            }
            return response;

        }

    }

    @Override
    public Response searchByPhoneNumber(long phoneNumber) {

        CandidateModel findingCandidateByPhoneNumber = candidateRepository.findByPhoneNumber(phoneNumber);
        if (findingCandidateByPhoneNumber != null){
            response.setMessage("Candidate Details Found : ");
            response.setObject(findingCandidateByPhoneNumber);
        }
        else {
            response.setMessage("Candidate Not Found");
            response.setObject(null);
        }
        return response;
    }

    @Override
    public Response getAllCandidateByMarksDescOrder() {

        List<CandidateModel> candidateDetails = candidateRepository.getAllCandidateByMarksInDescOrder();
        if( candidateDetails.size() > 0){
            response.setMessage("All Candidates Details Are :");
            response.setObject(candidateDetails);
        }
        else {
            response.setMessage("No Candidate Details Are Available ");
            response.setObject(null);
        }
        return response;

    }


}
