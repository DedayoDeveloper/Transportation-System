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
import java.util.List;


/**
 *
 * @author oreoluwa
 */
@Repository
public interface UserRepository extends JpaRepository<User , Long>{
    
    User findByPhonenumber(String phonenumber);

    User findByEmail(String email);

    User findByPhonenumberAndAndToken(String phonenumber, String token);

    User findByPasswordupdatetokenAndAndPhonenumber(String passwordgentokentime , String phonenumber);

    long deleteById(long id);



    @Query("select u from User u where u.phonenumber = :phonenumber")
    User getUserProfile(@Param("phonenumber") String phonenumber);

    @Modifying
    @Transactional
    @Query("update User u set u.passwordupdatetoken = :passwordupdatetoken, u.passwordgentokentime = :passwordgentokentime where u.phonenumber = :phonenumber")
    int uodatePasswordToken(@Param("passwordupdatetoken") String passwordupdatetoken,@Param("passwordgentokentime") Date passwordgentokentime,@Param("phonenumber") String phonenumber);


    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.phonenumber = :phonenumber")
    int changePassword (@Param("password") String password,@Param("phonenumber") String phonenumber);

    @Query("select u.passwordupdatetoken from User u where u.phonenumber = :phonenumber")
    String verifyPhonenumberToken(@Param("phonenumber") String phonenumber);


    @Transactional
    @Modifying
    @Query("update User u set u.enabled = 1 where u.phonenumber = :phonenumber")
    int authenticateUser (@Param("phonenumber") String phonenumber);


    @Query("select u.enabled from User u where u.phonenumber = :phonenumber")
    String getUserAuthorization(@Param("phonenumber") String phonenumber);



    @Transactional
    @Modifying
    @Query("update User u set u.fullname = :fullname, u.email = :email, u.password = :password, u.token = :token, u.otpgenerationtime = :otpgenerationtime where u.phonenumber = :phonenumber")
    int updateUserDetails(@Param("fullname") String fullname, @Param("email") String email, @Param("password") String password, @Param("token") String token,
                          @Param("otpgenerationtime") Date otpgenerationtime,
                          @Param("phonenumber") String phonenumber);









}
