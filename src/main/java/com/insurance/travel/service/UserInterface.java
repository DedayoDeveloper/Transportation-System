/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;

import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author adedayo
 */

public interface UserInterface {
    
    public User registerUser(User createuser);
   public User signIn(User userDetails);
   public String updateUserNextOfKin(String kinname,String kinphonenumber, String kinemail,String kinaddress,String phonenumber);
   public List<Trips> findTrips(String departure,String destination,String date);
    public TripBooking bookTrips(long id, String phonenumber, String fullname, String numberofseats);
    public Trips adminToCreateTripsForBooking(Trips trip);
    public TripBooking searchPassengerOnTrip(TripBooking trip);
    public List<TripBooking> getAllPassengersOnATrip(TripBooking searchTrip);
    public String sendTokenToUpdatePassword(String phonenumber);
    public int saveFileUploadPathToDatabase(String fileDownloadUri,String vehiclenumber);
    public String confirmPasswordResetToken(String phoneNumberVerificationToken, String phonenumber);
    public int getTotalAmountOfRegisteredUsers();
    public User createBusStationAdmin(User userAdmin);
//    public int getAllUsers();
public String updatePassword(String password,String phonenumber);
    public List<User> getListOfAllUsers();
    public String verifyTokenForUserAuthentication(String token,String phonenumber);
    public List<Trips> getListOfTripsBasedOnPriceInDescOrder(String departure,String destination,String date);
    public List<Trips> getAllTripsFilteredbyUser(BigDecimal price,String departure,String destination);
    public User getUserProfile(String phonenumber);
    public String getManifestFileToDownload(String departure,String destination,String date,String vehiclenumber,String transportcompany);
    public long deleteMobileUserAccountByAdmin(long id);
    public List<Trips> getListOfTripsBasedOnPriceInAscendingOrder(String departure,String destination,String date);
    public Trips getTripDetailsUsingId(long id);
    public String registerCoRiders(String coRiderName, String coRiderPhoneNumber,String phonenumber);
}
