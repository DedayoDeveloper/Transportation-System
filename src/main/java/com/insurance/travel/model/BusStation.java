package com.insurance.travel.model;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class BusStation {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "transportcompany", nullable = false)
    private String transportcompany;


    @Column(name = "station", nullable = false)
    private String station;


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

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }
}
