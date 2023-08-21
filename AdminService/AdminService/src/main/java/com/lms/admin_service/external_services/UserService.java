package com.lms.admin_service.external_services;

import com.lms.admin_service.dto.LoginDTO;
import com.lms.admin_service.responses.Response;
import com.lms.admin_service.responses.UserRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @PostMapping(value = "/user/externalLogin")
    Response externalLogin( LoginDTO loginDto);

}
