package com.insurance.travel.model;

import javax.persistence.Column;
import java.util.Arrays;

public class ExtraRiders {

    private String phonenumber;
    @Column(name = "numberofriders", nullable = true)
    private int numberofriders;
    private String[] coridername;
    private String[] coriderphonenumber;


    @Override
    public String toString() {
        return "ExtraRiders{" +
                "phonenumber='" + phonenumber + '\'' +
                ", numberofriders=" + numberofriders +
                ", coridername=" + Arrays.toString(coridername) +
                ", coriderphonenumber=" + Arrays.toString(coriderphonenumber) +
                '}';
    }


    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getNumberofriders() {
        return numberofriders;
    }

    public void setNumberofriders(int numberofriders) {
        this.numberofriders = numberofriders;
    }

    public String[] getCoridername() {
        return coridername;
    }

    public void setCoridername(String[] coridername) {
        this.coridername = coridername;
    }

    public String[] getCoriderphonenumber() {
        return coriderphonenumber;
    }

    public void setCoriderphonenumber(String[] coriderphonenumber) {
        this.coriderphonenumber = coriderphonenumber;
    }
}
