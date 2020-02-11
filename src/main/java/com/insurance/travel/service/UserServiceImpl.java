/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.service;

import com.insurance.travel.TrackTime;
import com.insurance.travel.model.CoRiders;
import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;

import com.insurance.travel.repository.CoRiderRepository;
import com.insurance.travel.repository.TripBookingRepository;
import com.insurance.travel.repository.TripsRepository;
import com.insurance.travel.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author adedayo
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
    private CoRiderRepository coRiders;
  
    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordencoder;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public static final String ACCOUNT_SID =
            "AC57e344c7aea908f9e5b230c995052ff3";
    public static final String AUTH_TOKEN =
            "0f196c60007c235bac3ddd343f3ee0e2";


    public void sendSmsMessage(String phonenumber,String body){
        logger.info("GOT TO TWILIO SERVICE TO SEND MESSAGE");
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(new PhoneNumber(phonenumber), // to
                        new PhoneNumber("+18177797043"), // from
                        body)
                .create();
        logger.info("MESSAGE STATUS INFO FROM TWILIO = " + message);
    }



    
    // SERVICE TO REGISTER NEW USER
    @Override
    @TrackTime
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


    // GET USER PROFILE
    @Override
    @TrackTime
    public User getUserProfile(String phonenumber){
        User user = repository.getUserProfile(phonenumber);
        if(user == null){throw new RuntimeException("No user details");}
        user.setPassword(null);
        user.setOtpgenerationtime(null);
        user.setToken(null);
        user.setPasswordgentokentime(null);
        user.setPasswordupdatetoken(null);
        return  user;
    }


    // VERIFY PHONE NUMBER TOKEN FOR USER AUTHENTICATION
    @Override
    @TrackTime
    public String verifyTokenForUserAuthentication(String token,String phonenumber){
        User getUserTokenDetails = repository.findByPhonenumberAndAndToken(phonenumber,token);
        if (getUserTokenDetails == null){
            throw new RuntimeException("Wrong token details");
        }
        Date otpGenTime = getUserTokenDetails.getOtpgenerationtime();
        Date otpRetTime = new Date();
        logger.info("OTP GENERATION TIME================= + " + otpGenTime);
        logger.info("OTP REQUEST SENT TIME================= + " + otpRetTime);
        long time1 = otpGenTime.getTime();
        logger.info(otpGenTime + " COVERTED TO MILLISECONDS == " + time1);
        long time2 = otpRetTime.getTime();
        logger.info(otpRetTime + " COVERTED TO MILLISECONDS == " + time2);
        long difference = time2 - time1;
        logger.info("TIME DIFFERENEC " + difference);

        if(difference > 1800000){
            throw new RuntimeException("Sorry User token only valid for 30 minutes");
        }
        int updateUser = repository.authenticateUser(phonenumber);
        return  "Verification successful";
    }





    // USER SIGN IN WITH PHONE-NUMBER AND PASSWORD
    @Override
    @TrackTime
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
    @TrackTime
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
    @TrackTime
    @Override
    public List<Trips> findTrips(String departure,String destination,String date){
    List<Trips> trip = tripsrepository.findByDepartureAndDestinationAndDate(departure,destination,date);
    if(trip.isEmpty())
        throw new RuntimeException("No trips avialable");
    return trip;
    }
    
    
    
    // SERVICE FOR USER TO BOOK TRIPS
    @Override
    @TrackTime
    public TripBooking bookTrips(long id, String phonenumber, String fullname, String numberofseats){
        Trips tripDetails = tripsrepository.findById(id);
        TripBooking bookTrip = new TripBooking();
        bookTrip.setTransportcompany(tripDetails.getTransportcompany());
        bookTrip.setDestination(tripDetails.getDestination());
        bookTrip.setDeparturedate(tripDetails.getDate());
        bookTrip.setPhonenumber(phonenumber);
        bookTrip.setFullname(fullname);
        bookTrip.setNumberofseats(numberofseats);
        bookTrip.setPrice(tripDetails.getPrice());
        bookTrip.setDeparture(tripDetails.getDeparture());
        tripbookrepo.save(bookTrip);
        return bookTrip;
    }


    // SERVICE TO SAVE CREATED TRIPS FROM ADMIN
    @Override
    @TrackTime
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
     trips.setDeparturepark(trip.getDeparturepark());
     trips.setArrivalpark(trip.getArrivalpark());
     return tripsrepository.save(trips);
    }



    // SEARCH FOR PASSENGER USING PHONENUMBER
    @Override
    @TrackTime
    public TripBooking searchPassengerOnTrip(TripBooking search) {
        TripBooking searchedPassenger = tripbookrepo.getPassengerRegisteredForTrip(search.getPhonenumber());
        if (searchedPassenger == null) {
            throw new RuntimeException("No passenger found");
        }

        return searchedPassenger;
    }


 
    @Override
    @TrackTime
    public List<TripBooking> getAllPassengersOnATrip(TripBooking searchTrip){
        List<TripBooking> getAllPassengers = tripbookrepo.getAllPassengersOnATrip(searchTrip.getTransportcompany(), searchTrip.getDestination());
        if(getAllPassengers == null){
            throw new RuntimeException("No passengers avialable or trip has not been registered");
        }
    return getAllPassengers;
    }



    @Override
    @TrackTime
    public String sendTokenToUpdatePassword(String phonenumber) {
        String value = "Password token not sent";
        User findPhonenumber = repository.findByPhonenumber(phonenumber);

        if(findPhonenumber == null){
            throw new RuntimeException("Phone number does not exist.");
        }
        Date passwordResetTokenTime = new Date();
        logger.info("PasswordResetToken Generation Time = " + passwordResetTokenTime);
        String phoneNumberVerificationToken = String.valueOf(Math.random()).substring(2, 6);

        // SEND SMS MESSAGE WITH TWILIO
        String body = "Please supply verification code " + phoneNumberVerificationToken + " to update password";
        sendSmsMessage(phonenumber,body);

        int updatePassword = repository.uodatePasswordToken(phoneNumberVerificationToken,passwordResetTokenTime,phonenumber);
        if(updatePassword > 0){
            value = "TOKEN SENT TO PHONE NUMBER SUCCESSFULLY";
        }
        return value;

    }


    @Override
    @TrackTime
    public String updatePassword(String password,String phonenumber){
        String response = "FAILED";
        User user = repository.findByPhonenumber(phonenumber);
        if (user == null){
            throw new RuntimeException("No registered user with given phonenumber.");
        }
        String encryptedpassword = passwordencoder.encode(password);
        int changePassword = repository.changePassword(encryptedpassword,phonenumber);
        if (changePassword > 0){
            response = "Password change successfull";
        }
        return response;
    }


    @Override
    @TrackTime
    public String confirmPasswordResetToken(String phoneNumberVerificationToken, String phonenumber){
        User userDetails = repository.findByPasswordupdatetokenAndAndPhonenumber(phoneNumberVerificationToken,phonenumber);
        String response = "FAILED";
        if (userDetails != null){
        Date getTokenTimeFromDb = userDetails.getPasswordgentokentime();
//            logger.info("TOKEN TIME FROM DB = " + getTokenTimeFromDb);
        long time1 = getTokenTimeFromDb.getTime();
            logger.info("TIME 1 " + time1);
        Date requestTime = new Date();
        long time2 = requestTime.getTime();
            logger.info("TIME2 = " + time2);
        long difference = time2 - time1;
            logger.info("TIME DIFFERENECE " + difference);
            if(difference > 1800000){
                throw new RuntimeException("Sorry User token only valid for 30 minutes");
            }
        response = "Password reset verification successful";
        } else {
            throw new RuntimeException("Wrong token details");
        }
        return response;
    }


    @Override
    @TrackTime
    public int saveFileUploadPathToDatabase(String filedownloaduri,String vehiclenumber){
        int uploadFileToDatabaseWithBusNumber = tripsrepository.uploadManifestWithTripBus(filedownloaduri,vehiclenumber);
            return uploadFileToDatabaseWithBusNumber;
    }


        // GET TOTAL AMOUNT OF REGISTERED USERS ON THIS PLATFORM
      @Override
      @TrackTime
    public int getTotalAmountOfRegisteredUsers(){
          List<User> getAllUserForAdmin = repository.findAll();
          int userSize = getAllUserForAdmin.size();
          return userSize;
      }



       @Override
       @TrackTime
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
        @TrackTime
        public List<User> getListOfAllUsers(){
           List<User> getAllUserForAdmin = repository.findAll();
           return getAllUserForAdmin;
    }

    @Override
    @TrackTime
    public List<Trips> getListOfTripsBasedOnPriceInDescOrder(String departure,String destination,String date){
        List<Trips> getList = tripsrepository.findAllByDepartureAndDestinationAndDateOrderByPriceDesc(departure,destination,date);
        if(getList.isEmpty()){
            throw new RuntimeException("No trips found");
        }
        return getList;

    }

    @Override
    @TrackTime
    public List<Trips> getListOfTripsBasedOnPriceInAscendingOrder(String departure,String destination,String date){
        List<Trips> getList = tripsrepository.findAllByDepartureAndDestinationAndDateOrderByPriceAsc(departure,destination,date);
        if(getList.isEmpty()){
            throw new RuntimeException("No trips found");
        }
        return getList;
    }


    @Override
    @TrackTime
    public List<Trips> getAllTripsFilteredbyUser(BigDecimal price,String departure,String destination){
        List<Trips> getAllFilteredTrips = tripsrepository.getAllFilteredTrips(price,departure,destination);
        if(getAllFilteredTrips.isEmpty()){
            throw new RuntimeException("No trips available");
        }
        return getAllFilteredTrips;
    }



    @Override
    @TrackTime
    public String getManifestFileToDownload(String departure,String destination,String date,String vehiclenumber,String transportcompany){
        String getFileDownloadUri = tripsrepository.getTripFileManifestToDownload(departure,destination,date,vehiclenumber,transportcompany);
        if(getFileDownloadUri == null){throw new RuntimeException("No trips found");}
        return getFileDownloadUri;
    }

    // ADMIN TO DELETE USER
    @Override
    @TrackTime
    public long deleteMobileUserAccountByAdmin(long id){
        long deleteUser = repository.deleteById(id);
        if(deleteUser == 0){throw new RuntimeException("No user deleted");}
        return deleteUser;
    }

    @Override
    @TrackTime
    public Trips getTripDetailsUsingId(long id){
        Trips getTripDetails = tripsrepository.findById(id);
        if (getTripDetails == null){
            throw new RuntimeException("No Trips Found");
        }
        return getTripDetails;
    }


    @Override
    @TrackTime
    public String registerCoRiders(String coRiderName, String coRiderPhoneNumber,String phonenumber){
        User checkUser = repository.findByPhonenumber(phonenumber);
        logger.info("DEBUGGING!!");
        if(checkUser == null) {
            throw new RuntimeException("No User Found");
        }

        CoRiders riders = new CoRiders();
        riders.setCoridername(coRiderName);
        riders.setCoriderphonenumber(coRiderPhoneNumber);
        riders.setPhonenumber(phonenumber);
        coRiders.save(riders);
        return "Succesfully Saved";
    }



}
