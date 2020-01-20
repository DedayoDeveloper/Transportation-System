/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.repository;

import com.insurance.travel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;


/**
 *
 * @author oreoluwa
 */
@Repository
public interface UserRepository extends JpaRepository<User , Long>{
    
    User findByPhonenumber(String phonenumber);
    User findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.passwordupdatetoken = :passwordupdatetoken where u.phonenumber = :phonenumber")
    int uodatePasswordToken(@Param("passwordupdatetoken") String passwordupdatetoken, @Param("phonenumber") String phonenumber);


    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.passwordupdatetoken = :passwordupdatetoken and u.phonenumber = :phonenumber")
    int changePassword (@Param("password") String password,@Param("passwordupdatetoken") String passwordupdatetoken,@Param("phonenumber") String phonenumber);

    @Query("select u.passwordupdatetoken from User u where u.phonenumber = :phonenumber")
    String verifyPhonenumberToken(@Param("phonenumber") String phonenumber);


    @Transactional
    @Modifying
    @Query("update User u set u.enabled = 1 where u.phonenumber = :phonenumber")
    int authenticateUser (@Param("phonenumber") String phonenumber);


    @Query("select u.enabled from User u where u.phonenumber = :phonenumber")
    String getUserAuthorization(@Param("phonenumber") String phonenumber);

    User findByPhonenumberAndAndToken(String phonenumber, String token);

    @Transactional
    @Modifying
    @Query("update User u set u.fullname = :fullname, u.email = :email, u.password = :password, u.token = :token, u.otpgenerationtime = :otpgenerationtime where u.phonenumber = :phonenumber")
    int updateUserDetails(@Param("fullname") String fullname, @Param("email") String email, @Param("password") String password, @Param("token") String token,
                          @Param("otpgenerationtime") Date otpgenerationtime,
                          @Param("phonenumber") String phonenumber);









}
