/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.response;

import org.springframework.http.HttpStatus;

/**
 *
 * @author oreoluwa
 */
public class ApiResponse<T> {
    
    private T response;
    private HttpStatus status;
    private String message;
    private String error;
    private String responsecode;
    private int count;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
