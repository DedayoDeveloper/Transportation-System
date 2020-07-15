/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.controller;

import com.insurance.travel.model.User;
import com.insurance.travel.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author oreoluwa
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ApiResponse<User> springHandleNotFound(HttpServletResponse response, Exception ex) throws IOException {
        ApiResponse<User> error = new ApiResponse<>();
        error.setStatus(HttpStatus.NOT_FOUND);
        error.setError("Error Found");
        error.setMessage(ex.getMessage());
        error.setResponsecode("99");
        return error;
    }
    
}
