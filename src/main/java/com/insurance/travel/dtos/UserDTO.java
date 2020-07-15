package com.insurance.travel.dtos;

import java.util.Date;

public class UserDTO {


    private String fullname;
    private Date otpgenerationtime;
    private String phonenumber;
    private String email;
    private String password;
    private String kinname;
    private String kinphonenumber;
    private String kinemail;
    private String kinaddress;
    private String passwordupdatetoken;
    private int enabled;
    private String role;
    private Date passwordgentokentime;
    private String token;
    private String isFirstTimeLogin;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getOtpgenerationtime() {
        return otpgenerationtime;
    }

    public void setOtpgenerationtime(Date otpgenerationtime) {
        this.otpgenerationtime = otpgenerationtime;
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

    public String getPasswordupdatetoken() {
        return passwordupdatetoken;
    }

    public void setPasswordupdatetoken(String passwordupdatetoken) {
        this.passwordupdatetoken = passwordupdatetoken;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getPasswordgentokentime() {
        return passwordgentokentime;
    }

    public void setPasswordgentokentime(Date passwordgentokentime) {
        this.passwordgentokentime = passwordgentokentime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIsFirstTimeLogin() {
        return isFirstTimeLogin;
    }

    public void setIsFirstTimeLogin(String isFirstTimeLogin) {
        this.isFirstTimeLogin = isFirstTimeLogin;
    }
}
