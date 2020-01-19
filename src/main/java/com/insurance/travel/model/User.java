/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

/**
 *
 * @author oreoluwa
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "fullname", nullable = false)
    private String fullname;

    @Column(name = "otpgenerationtime", nullable = false)
    private Date otpgenerationtime;



    @Column(name = "phonenumber", nullable = false)
    private String phonenumber;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "kinname", nullable = true)
    private String kinname;

    @Column(name = "kinphonenumber", nullable = true)
    private String kinphonenumber;

    @Column(name = "kinemail", nullable = true)
    private String kinemail;

    @Column(name = "kinaddress", nullable = true)
    private String kinaddress;

    @Column(name = "passwordupdatetoken", nullable = true)
    private String passwordupdatetoken;

    @Column(name = "enabled" , nullable = false)
    private int enabled;

    @Column(name = "role", nullable = true)
    private String role;

    public Date getOtpgenerationtime() {
        return otpgenerationtime;
    }

    public void setOtpgenerationtime(Date otpgenerationtime) {
        this.otpgenerationtime = otpgenerationtime;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Column(name = "token", nullable = true)
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPasswordupdatetoken() {
        return passwordupdatetoken;
    }

    public void setPasswordupdatetoken(String passwordupdatetoken) {
        this.passwordupdatetoken = passwordupdatetoken;
    }

    public String getKinname() {
        return kinname;
    }

    public void setKinname(String kinname) {
        this.kinname = kinname;
    }

    public String getKinphonenumber() {
        return kinphonenumber;
    }

    public void setKinphonenumber(String kinphonenumber) {
        this.kinphonenumber = kinphonenumber;
    }

    public String getKinemail() {
        return kinemail;
    }

    public void setKinemail(String kinemail) {
        this.kinemail = kinemail;
    }

    public String getKinaddress() {
        return kinaddress;
    }

    public void setKinaddress(String kinaddress) {
        this.kinaddress = kinaddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
