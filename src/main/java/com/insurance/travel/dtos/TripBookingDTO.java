package com.insurance.travel.dtos;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

public class TripBookingDTO {

    private long id;
    private String transportcompany;
    private String departure;
    private String destination;
    private Date departuredate;
    private BigDecimal price;
    private int extrariders;
    private String fullname;
    private String phonenumber;
    private String paymentreferenceid;
    private String paymentstatus;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransportcompany() {
        return transportcompany;
    }

    public void setTransportcompany(String transportcompany) {
        this.transportcompany = transportcompany;
    }

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
