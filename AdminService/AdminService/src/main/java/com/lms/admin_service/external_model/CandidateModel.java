package com.lms.admin_service.external_model;

import lombok.Data;


@Data
public class CandidateModel {


    private String firstname;
    private String lastname;
    private String city;
    private String email;
    private long phoneNumber;
    private float marks;
    private int fees;
    private String hiringStatus;

}
