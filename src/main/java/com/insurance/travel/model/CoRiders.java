package com.insurance.travel.model;


import javax.persistence.*;

@Entity
@Table(name = "coriders")
public class CoRiders {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private long id;


    private String phonenumber;
    @Column(name = "numberofriders", nullable = true)
    private int numberOfRiders;
    private String[] ridername;
    private String[] riderphonenumber;


    @Override
    public String toString() {
        return "coRiders : [ridername=" + ridername + ", riderphonenumber=" + riderphonenumber + "]";
    }

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

    public String[] getRidername() {
        return ridername;
    }

    public void setRidername(String[] ridername) {
        this.ridername = ridername;
    }

    public String[] getRiderphonenumber() {
        return riderphonenumber;
    }

    public void setRiderphonenumber(String[] riderphonenumber) {
        this.riderphonenumber = riderphonenumber;
    }

    public int getNumberOfRiders() {
        return numberOfRiders;
    }

    public void setNumberOfRiders(int numberOfRiders) {
        this.numberOfRiders = numberOfRiders;
    }
}
