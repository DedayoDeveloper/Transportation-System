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

/**
 *
 * @author oreoluwa
 */

@Entity
@Table(name = "tbl_trips")
@EntityListeners(AuditingEntityListener.class)
public class Trips {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    
    @Column(name = "destination", nullable = true)
    private String destination;
    
    @Column(name = "departure", nullable = true)
    private String departure;
    
    @Column(name = "date", nullable = true)
    private String date;
    
    @Column(name = "price", nullable = true)
    private BigDecimal price;
    
    @Column(name = "seats", nullable = true)
    private String seats;
    
    @Column(name = "transportcompany", nullable = true)
    private String transportcompany;
    
    @Column(name = "vehiclenumber", nullable = true)
    private String vehiclenumber;
    
    @Column(name = "vehicletype", nullable = true)
    private String vehicletype;
    
    @Column(name = "departuretime", nullable = true)
    private String time;

    @Column(name = "drivername", nullable = true)
    private String drivername;

    @Column(name = "filedownloaduri", nullable = true)
    private String filedownloaduri;

//    @Column(name = "station", nullable = true)
//    private String station;


    @Column(name = "departurepark", nullable = true)
    private String departurepark;

    @Column(name = "arrivalpark", nullable = true)
    private String arrivalpark;

    public String getDeparturepark() {
        return departurepark;
    }

    public void setDeparturepark(String departurepark) {
        this.departurepark = departurepark;
    }

    public String getArrivalpark() {
        return arrivalpark;
    }

    public void setArrivalpark(String arrivalpark) {
        this.arrivalpark = arrivalpark;
    }

//    public String getStation() {
//        return station;
//    }
//
//    public void setStation(String station) {
//        this.station = station;
//    }

    public String getFiledownloaduri() {
        return filedownloaduri;
    }

    public void setFiledownloaduri(String filedownloaduri) {
        this.filedownloaduri = filedownloaduri;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }
    
    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getTransportcompany() {
        return transportcompany;
    }

    public void setTransportcompany(String transportcompany) {
        this.transportcompany = transportcompany;
    }


}
