/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;


import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;
import com.insurance.travel.repository.TripBookingRepository;
import com.insurance.travel.repository.TripsRepository;
import com.insurance.travel.repository.UserRepository;
import java.util.List;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author oreoluwa
 */
@Service
public class UserServiceImpl implements UserInterface{
    
    
    
    @Autowired
    private UserRepository repository;
    
    @Autowired
    private TripsRepository tripsrepository;
    
    @Autowired
    private TripBookingRepository tripbookrepo;
  
    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordencoder;

    public static final String ACCOUNT_SID =
            "AC57e344c7aea908f9e5b230c995052ff3";
    public static final String AUTH_TOKEN =
            "0f196c60007c235bac3ddd343f3ee0e2";
    
    
    
    
    // SERVICE TO REGISTER NEW USER
    @Override
    public User registerUser(User createuser){
    User user = new User();
    user.setFullname(createuser.getFullname());
    
    User checkPhonenumber = repository.findByPhonenumber(createuser.getPhonenumber());
    if(checkPhonenumber != null){ throw new RuntimeException("Phone number registered to an existing user");}
    user.setPhonenumber(createuser.getPhonenumber());
    user.setEmail(createuser.getEmail());
    User checkEmail = repository.findByEmail(createuser.getEmail());
    if(checkEmail != null){ throw new RuntimeException("Email registered to an existing user");}
    user.setPassword(passwordencoder.encode(createuser.getPassword()));
    user.setKinname(createuser.getKinname());
    user.setKinphonenumber(createuser.getKinphonenumber());
  // SEND SMS MESSAGE WITH TWILIO
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(user.getPhonenumber()), // to
                        new PhoneNumber("+18177797043"), // from
                        "HELLO THIS IS A TEXT MESSAGE APPLICATION TEST")
                .create();

    user.setKinemail(createuser.getKinemail());
    user.setKinaddress(createuser.getKinaddress());
    return repository.save(user);
    }
    
    
    // USER SIGN IN WITH PHONE-NUMBER AND PASSWORD
    @Override
    public User signIn(String phonenumber,String password){
    User user = repository.findByPhonenumber(phonenumber);
    if (user == null) throw new RuntimeException("Invalid user details");
    
      boolean passwordcheck = passwordencoder.matches(password,user.getPassword());
    
    if(passwordcheck == false){
            throw new RuntimeException("Invalid Password");
        }
    return user;
    }
    
    
    // UPDATE USER NEXT OF KIN DETAILS
    @Override
    public String updateUserNextOfKin(String kinname,String kinphonenumber, String kinemail,String kinaddress,String phonenumber){
        User user = repository.findByPhonenumber(phonenumber);
        
        if(user == null)
            throw new RuntimeException("Invalid phone number");
        
        if(kinname != null){
           user.setKinname(kinname);
        }
        if(kinaddress != null){
            user.setKinaddress(kinaddress);
        }
        if(kinphonenumber != null){
          user.setKinphonenumber(kinphonenumber);
        }
        if(kinemail != null){
          user.setKinemail(kinemail);
        }
        repository.save(user);
    return "Profile successfully updated";
    }
    
    
    // SERVICE FOR USER TO FIND TRIPS
    @Override
    public Trips findTrips(String departure,String destination,String date){
    Trips trip = tripsrepository.getTripsAvialable(departure,destination,date);
    if(trip == null)
        throw new RuntimeException("No trips avialable");
    return trip;
    }
    
    
    
    // SERVICE FOR USER TO BOOK TRIPS
    @Override
    public TripBooking bookTrips(TripBooking book){
    TripBooking trip = new TripBooking();
    trip.setBoarding(book.getBoarding());
    trip.setDeparturedate(book.getDeparturedate());
    trip.setDestination(book.getDestination());
    trip.setPrice(book.getPrice());
    trip.setNumberofseats(book.getNumberofseats());
    trip.setFullname(book.getFullname());
    trip.setPhonenumber(book.getPhonenumber());
    return tripbookrepo.save(trip);
    }
    
    // SERVICE TO SAVE CREATED TRIPS FROM ADMIN
    @Override
    public Trips adminToCreateTripsForBooking(Trips trip){
     Trips trips = new Trips();
     trips.setTransportcompany(trip.getTransportcompany());
     trips.setDeparture(trip.getDeparture());
     trips.setDestination(trip.getDestination());
     trips.setDate(trip.getDate());
     trips.setPrice(trip.getPrice());
     trips.setSeats(trip.getSeats());
     trips.setVehiclenumber(trip.getVehiclenumber());
     trips.setVehicletype(trip.getVehicletype());
     trips.setTime(trip.getTime());
     trips.setDrivername(trip.getDrivername());
     return tripsrepository.save(trips);
    }



    // SEARCH FOR PASSENGER USING PHONENUMBER
    @Override
    public TripBooking searchPassengerOnTrip(TripBooking search) {
        TripBooking searchedPassenger = tripbookrepo.getPassengerRegisteredForTrip(search.getPhonenumber());
        if (searchedPassenger == null) {
            throw new RuntimeException("No passenger found");
        }

        return searchedPassenger;
    }


 
    @Override
    public List<TripBooking> getAllPassengersOnATrip(TripBooking searchTrip){
        List<TripBooking> getAllPassengers = tripbookrepo.getAllPassengersOnATrip(searchTrip.getBoarding(), searchTrip.getDestination());
        if(getAllPassengers == null){
            throw new RuntimeException("No passengers avialable or trip has not been registered");
        }
    return getAllPassengers;
    }



    @Override
    public String sendTokenToUpdatePassword(String phonenumber) {

        String value = "Password token not sent";

        String phoneNumberVerificationToken = String.valueOf(Math.random()).substring(2, 6);
        // SEND SMS MESSAGE WITH TWILIO
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber(phonenumber), // to
                        new PhoneNumber("+18177797043"), // from
                        "HERE IS YOUR TOKEN TO RESET PASSWORD  " + phoneNumberVerificationToken)
                .create();

        int updatePassword = repository.uodatePasswordToken(phoneNumberVerificationToken,phonenumber);
        if(updatePassword > 0){
            value = "TOKEN SENT TO PHONE NUMBER SUCCESSFULLY";
        }
        return value;

    }


    @Override
    public String changePassword(String password,String phoneNumberVerificationToken, String phonenumber){
        String response = "FAILED";
        String encryptpassword = passwordencoder.encode(password);
        String verifyToken = repository.verifyPhonenumberToken(phonenumber);
        if(verifyToken.contains(phoneNumberVerificationToken)){
        int changePassword = repository.changePassword(encryptpassword,phoneNumberVerificationToken,phonenumber);
        if(changePassword > 0) {
            response = "Password changed successfully";
        }
        } else {
            throw new RuntimeException("Wrong token details");
        }
        return response;
    }


    @Override
    public int saveFileUploadPathToDatabase(String filedownloaduri,String vehiclenumber){
        System.out.println();
        System.out.println("VEHICLE NUMBER: " + vehiclenumber);
        int uploadFileToDatabaseWithBusNumber = tripsrepository.uploadManifestWithTripBus(filedownloaduri,vehiclenumber);
        System.out.println("uploadFileToDatabaseWithBusNumber == " + uploadFileToDatabaseWithBusNumber);
            return uploadFileToDatabaseWithBusNumber;
    }












}
