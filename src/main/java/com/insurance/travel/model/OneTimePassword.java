package com.insurance.travel.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OTP")
@EntityListeners(AuditingEntityListener.class)
public class OneTimePassword {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String otp;
    private Date createdate;
    private Date expirydate;

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(Date expirydate) {
        this.expirydate = expirydate;
    }



}
