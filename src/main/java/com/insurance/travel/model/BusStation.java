package com.insurance.travel.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "tbl_busstations")
@EntityListeners(AuditingEntityListener.class)
public class BusStation {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

   @Column(name="address", nullable = true)
   private String address;

   @Column(name = "state",nullable = false)
   private String state;


    @Column(name = "station", nullable = false)
    private String station;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
