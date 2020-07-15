/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;

import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author adedayo
 */

 public interface UserInterface {
    
     User registerUser(User createuser);
     String resendOtpTokenForAuthentication(String phoneNumber);
     User signIn(String phoneNumber , String password);
    String updateUserNextOfKin(String kinname,String kinphonenumber, String kinemail,String kinaddress,String phonenumber);
    List<Trips> findTrips(String departure, String destination, Date date);
     TripBooking bookTrips(long id, String phonenumber,int numberOfRiders,String paymentreferenceid,String fullname);
     Trips adminToCreateTripsForBooking(Trips trip);
     TripBooking searchPassengerOnTrip(TripBooking trip);
     List<TripBooking> getAllPassengersOnATrip(TripBooking searchTrip);
     String sendTokenToUpdatePassword(String phonenumber);
     int saveFileUploadPathToDatabase(String fileDownloadUri,String vehiclenumber);
     String confirmPasswordResetToken(String phoneNumberVerificationToken, String phonenumber);
     int getTotalAmountOfRegisteredUsers();
     User createBusStationAdmin(User userAdmin);
     String updatePassword(String password,String phonenumber);
     List<User> getListOfAllUsers();
     String verifyTokenForUserAuthentication(String token,String phonenumber);
     List<Trips> getListOfTripsBasedOnPriceInDescOrder(String departure, String destination, Date date);
     List<Trips> getAllTripsFilteredbyUser(BigDecimal price,String departure,String destination);
     User getUserProfile(String phonenumber);
     String getManifestFileToDownload(String departure, String destination, Date date, String vehiclenumber, String transportcompany);
     long deleteMobileUserAccountByAdmin(long id);
     List<Trips> getListOfTripsBasedOnPriceInAscendingOrder(String departure, String destination, Date date);
     Trips getTripDetailsUsingId(long id);
     String registerCoRiders(String coRiderName, String coRiderPhoneNumber,String phonenumber);
     List<TripBooking> getBookedTrips(String phonenumber);
     User adminSignIn(String phoneNumber, String password);
     User busAgentAdminSignIn(String phoneNumber, String password);
     User updateBusAdminPassword(String phoneNumber , String newPassword);
}
