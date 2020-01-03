/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author oreoluwa
 */
@Service
public class myUserDetailsService implements UserDetailsService{
    
     @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User("longbridge","12345", new ArrayList<>());
    }
    
}
