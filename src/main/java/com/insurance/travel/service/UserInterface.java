/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;

import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;
import java.util.List;

/**
 *
 * @author oreoluwa
 */
public interface UserInterface {
    
    public User registerUser(User createuser);
   public User signIn(String phonenumber,String password);
   public String updateUserNextOfKin(String kinname,String kinphonenumber, String kinemail,String kinaddress,String phonenumber);
   public Trips findTrips(String departure,String destination,String date);
   public TripBooking bookTrips(TripBooking book);
    public Trips adminToCreateTripsForBooking(Trips trip);
    public TripBooking searchPassengerOnTrip(TripBooking trip);
    public List<TripBooking> getAllPassengersOnATrip(TripBooking searchTrip);
    public String sendTokenToUpdatePassword(String phonenumber);
    public int saveFileUploadPathToDatabase(String fileDownloadUri,String vehiclenumber);
    public String changePassword(String password,String phoneNumberVerificationToken, String phonenumber);
}
