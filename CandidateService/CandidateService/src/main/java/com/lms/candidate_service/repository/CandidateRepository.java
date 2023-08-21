package com.lms.candidate_service.repository;

import com.lms.candidate_service.entity.CandidateModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CandidateRepository extends JpaRepository<CandidateModel,String> {

    List<CandidateModel> findAllByFirstnameAndLastname (String firstname, String lastname);
    CandidateModel findByPhoneNumber (long phoneNumber);
    List<CandidateModel> findAllByFirstname (String firstname);

    @Query("Select candidates from CandidateModel candidates ORDER BY candidates.marks DESC")
    List<CandidateModel> getAllCandidateByMarksInDescOrder ();


}
