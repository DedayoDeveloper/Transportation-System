package com.insurance.travel.model;


import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "coriders")
public class CoRiders {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;


    private String phonenumber;
    private String coridername;
    private String coriderphonenumber;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCoridername() {
        return coridername;
    }

    public void setCoridername(String coridername) {
        this.coridername = coridername;
    }

    public String getCoriderphonenumber() {
        return coriderphonenumber;
    }

    public void setCoriderphonenumber(String coriderphonenumber) {
        this.coriderphonenumber = coriderphonenumber;
    }



    @Override
    public String toString() {
        return "CoRiders{" +
                "id=" + id +
                ", phonenumber='" + phonenumber + '\'' +
                ", coridername=" + coridername +
                ", coriderphonenumber=" + coriderphonenumber +
                '}';
    }
}
