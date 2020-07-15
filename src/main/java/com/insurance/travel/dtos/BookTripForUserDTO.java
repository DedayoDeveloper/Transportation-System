package com.insurance.travel.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class BookTripForUserDTO {


    private String departure;
    private String destination;
    private Date departuredate;
    private BigDecimal price;
    private int extrariders;
    private String fullname;
    private String phonenumber;
    private String paymentreferenceid;
    private String paymentstatus;


    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(Date departuredate) {
        this.departuredate = departuredate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getExtrariders() {
        return extrariders;
    }

    public void setExtrariders(int extrariders) {
        this.extrariders = extrariders;
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

    public String getPaymentreferenceid() {
        return paymentreferenceid;
    }

    public void setPaymentreferenceid(String paymentreferenceid) {
        this.paymentreferenceid = paymentreferenceid;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }
}
