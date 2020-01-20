/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;


import com.insurance.travel.controller.FirstController;
import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;
import com.insurance.travel.repository.TripBookingRepository;
import com.insurance.travel.repository.TripsRepository;
import com.insurance.travel.repository.UserRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.preview.accSecurity.service.Verification;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author oreoluwa
 */
@Configuration
@EnableScheduling
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String ACCOUNT_SID =
            "AC57e344c7aea908f9e5b230c995052ff3";
    public static final String AUTH_TOKEN =
            "0f196c60007c235bac3ddd343f3ee0e2";


    public void sendSmsMessage(String phonenumber,String body){
        System.out.println("GOT TO TWILIO SERVICE TO SEND MESSAGE");
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber(phonenumber), // to
                        new PhoneNumber("+18177797043"), // from
                        body)
                .create();
        System.out.println("MESSAGE STATUS INFO FROM TWILIO = " + message);
    }



    
    // SERVICE TO REGISTER NEW USER
    @Override
    public User registerUser(User createuser){
    User user = new User();
        String otp = String.valueOf(Math.random()).substring(2, 6);
        Date otpGenerationTime = new Date();
        User checkPhonenumber = repository.findByPhonenumber(createuser.getPhonenumber());
        if(checkPhonenumber != null){
        String checkUserAuthorization = repository.getUserAuthorization(createuser.getPhonenumber());
        if (checkUserAuthorization.contains("1")){
            throw new RuntimeException("Phone-number registered to an existing user");
        }
        int updateUserDetails = repository.updateUserDetails(createuser.getFullname(),createuser.getEmail(),passwordencoder.encode(createuser.getPassword()), otp,
                otpGenerationTime,
                createuser.getPhonenumber());
        if(updateUserDetails == 1){

            // SEND SMS MESSAGE WITH TWILIO API
            String body = " Dear " + createuser.getFullname() + " your profile has been created please submit token " + otp + "to log in, token valid for 30 minutes";
            sendSmsMessage(createuser.getPhonenumber(), body);

            return createuser;
        }

    }
    String role = "ROLE_USER";
    user.setFullname(createuser.getFullname());
    user.setOtpgenerationtime(otpGenerationTime);
    user.setPhonenumber(createuser.getPhonenumber());
    user.setEmail(createuser.getEmail());
    User checkEmail = repository.findByEmail(createuser.getEmail());
    if(checkEmail != null){ throw new RuntimeException("Email registered to an existing user");}
    user.setPassword(passwordencoder.encode(createuser.getPassword()));
    user.setKinname(createuser.getKinname());
    user.setKinphonenumber(createuser.getKinphonenumber());

  // SEND SMS MESSAGE WITH TWILIO API
        String body = " Dear " + createuser.getFullname() + " your profile has been created please submit token " + otp + " to log in, token valid for 30 minutes";
        sendSmsMessage(createuser.getPhonenumber(), body);

    user.setToken(otp);
    user.setKinemail(createuser.getKinemail());
    user.setKinaddress(createuser.getKinaddress());
    user.setRole(role);
    return repository.save(user);
    }




    // VERIFY PHONE NUMBER TOKEN FOR USER AUTHENTICATION
    @Override
    public String verifyTokenForUserAuthentication(String token,String phonenumber){
        User getUserTokenDetails = repository.findByPhonenumberAndAndToken(phonenumber,token);
        if (getUserTokenDetails == null){
            throw new RuntimeException("Wrong token details");
        }
        Date otpGenTime = getUserTokenDetails.getOtpgenerationtime();
        Date otpRetTime = new Date();
        System.out.println("OTP GENERATION TIME================= + " + otpGenTime);
        System.out.println("OTP REQUEST SENT TIME================= + " + otpRetTime);
        long time1 = otpGenTime.getTime();
        System.out.println(otpGenTime + " COVERTED TO MILLISECONDS == " + time1);
        long time2 = otpRetTime.getTime();
        System.out.println(otpRetTime + " COVERTED TO MILLISECONDS == " + time2);
        long difference = time1 - time2;
        System.out.println("TIME DIFFERENEC " + difference);

        if(difference < -1800000){
            throw new RuntimeException("Sorry User token only valid for 30 minutes");
        }
        int updateUser = repository.authenticateUser(phonenumber);
        return  "Verification successful";
    }





    // USER SIGN IN WITH PHONE-NUMBER AND PASSWORD
    @Override
    public User signIn(User userDetails){
    User user = repository.findByPhonenumber(userDetails.getPhonenumber());
    if (user == null){ throw new RuntimeException("Invalid user details"); }
    boolean passwordcheck = passwordencoder.matches(userDetails.getPassword(),user.getPassword());
    if(passwordcheck == false){
            throw new RuntimeException("Invalid Password");
        }
      int authorized = user.getEnabled();

            if (authorized == 0)
            {
                throw new RuntimeException("You are not authorized to log in");
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
        String body = "Please supply verification code " + phoneNumberVerificationToken + " to update password";
        sendSmsMessage(phonenumber,body);

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
        int uploadFileToDatabaseWithBusNumber = tripsrepository.uploadManifestWithTripBus(filedownloaduri,vehiclenumber);
        System.out.println("uploadFileToDatabaseWithBusNumber == " + uploadFileToDatabaseWithBusNumber);
            return uploadFileToDatabaseWithBusNumber;
    }


        // GET TOTAL AMOUNT OF REGISTERED USERS ON THIS PLATFORM
      @Override
    public int getTotalAmountOfRegisteredUsers(){
          List<User> getAllUserForAdmin = repository.findAll();
          int userSize = getAllUserForAdmin.size();
          return userSize;
      }



       @Override
       public User createBusStationAdmin(User userAdmin){
        String role = "ROLE_BUSADMIN";
         User user = new User();
         user.setFullname(userAdmin.getFullname());
         user.setPhonenumber(userAdmin.getPhonenumber());

         // SEND SMS MESSAGE WITH TWILIO
           String body = "Your supervisor account has been created successfully";
           sendSmsMessage(userAdmin.getPhonenumber(),body);

           user.setEmail(userAdmin.getEmail());
         user.setRole(role);
         user.setPassword(passwordencoder.encode(userAdmin.getPassword()));
         repository.save(user);
         return  user;
      }


        @Override
        public List<User> getListOfAllUsers(){
           List<User> getAllUserForAdmin = repository.findAll();
           return getAllUserForAdmin;
    }







}
