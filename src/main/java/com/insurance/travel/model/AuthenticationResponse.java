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
    
      public AuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }
    
    

    public String getJwt() {
        return jwt;
    }
    
    
}
