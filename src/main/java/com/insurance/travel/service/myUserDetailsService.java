/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;

import java.util.ArrayList;

import com.insurance.travel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    UserRepository repository;
    
     @Override
    public UserDetails loadUserByUsername(String phonenumber) throws UsernameNotFoundException {
                 com.insurance.travel.model.User user = repository.findByPhonenumber(phonenumber);

                 if (user == null){
                     throw new UsernameNotFoundException("User Not Found " + phonenumber);
                 }

         return new org.springframework.security.core.userdetails.User(user.getPhonenumber(), user.getPassword(),
                 new ArrayList<>());
    }
    
}
