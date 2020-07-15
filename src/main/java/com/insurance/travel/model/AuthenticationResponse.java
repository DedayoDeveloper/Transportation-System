/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.model;

/**
 *
 * @author oreoluwa
 */
public class AuthenticationResponse {
    
    private final String jwt;
    private final User user;
    private final String isFirstTimeLogin;


    public AuthenticationResponse(String jwt, User userDetails, String isFirstTimeLogin) {
        this.jwt = jwt;
        this.user = userDetails;
        this.isFirstTimeLogin = isFirstTimeLogin;
    }

    public String getIsFirstTimeLogin() {
        return isFirstTimeLogin;
    }

    public String getJwt() {
        return jwt;
    }

    public User getUser() {
        return user;
    }
}
