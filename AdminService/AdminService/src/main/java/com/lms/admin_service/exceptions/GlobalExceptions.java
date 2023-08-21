package com.lms.admin_service.exceptions;

import com.lms.admin_service.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions extends Exception{

    @Autowired
    private Response response;

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Response> globalExceptionHandler (UserException userException){

        response.setMessage(userException.getMessage());
        return new ResponseEntity<>(response,userException.getStatus());

    }
}
