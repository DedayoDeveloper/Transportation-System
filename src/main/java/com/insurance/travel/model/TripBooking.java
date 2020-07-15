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

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author oreoluwa
 */
@Entity
@Table(name = "tbl_tripbooking")
@EntityListeners(AuditingEntityListener.class)
public class TripBooking {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "transportcompany", nullable = true)
    private String transportcompany;

    @Column(name = "departure" , nullable = false)
    private String departure;

    @Column(name = "destination", nullable = false)
    private String destination;
    
    @Column(name = "departuredate", nullable = false)
    private Date departuredate;
    
    @Column(name = "price", nullable = true)
    private BigDecimal price;
    
    @Column(name = "extrariders", nullable = false)
    private int extrariders;
    
    
    @Column(name = "fullname", nullable = false)
    private String fullname;
    
    @Column(name = "phonenumber", nullable = false)
    private String phonenumber;

    @Column(name = "paymentreferenceid", nullable = true)
    private String paymentreferenceid;

    @Column(name = "paymentstatus", nullable = true)
    private String paymentstatus;

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

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
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
    

    
}
