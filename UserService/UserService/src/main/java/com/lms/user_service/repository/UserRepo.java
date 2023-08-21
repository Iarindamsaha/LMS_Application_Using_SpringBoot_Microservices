package com.lms.user_service.repository;

import com.lms.user_service.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail (String email);
    UserEntity findByEmailAndPassword (String email, String password);
    UserEntity findByEmailAndOtp (String email, int otp);
}
