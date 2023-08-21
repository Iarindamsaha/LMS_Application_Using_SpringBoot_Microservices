package com.lms.user_service.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private int otp;
    private String role;

}
