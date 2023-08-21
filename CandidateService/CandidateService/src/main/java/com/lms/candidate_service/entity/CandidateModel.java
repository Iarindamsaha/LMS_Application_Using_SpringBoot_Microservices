package com.lms.candidate_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "candidate_details")
public class CandidateModel {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String city;
    private String email;
    private long phoneNumber;
    private float marks;
    private int fees;
    private String hiringStatus;

}
