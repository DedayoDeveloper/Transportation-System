/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.insurance.travel.fileupload.FileStorageService;
import com.insurance.travel.fileupload.Response;
import com.insurance.travel.model.*;
import com.insurance.travel.repository.UserRepository;
import com.insurance.travel.response.ApiResponse;
import com.insurance.travel.service.UserInterface;
import com.insurance.travel.service.UserServiceImpl;
import com.insurance.travel.service.myUserDetailsService;
import com.insurance.travel.tokenConfig.JwtUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author adedayo
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class FirstController {
    
    
     @Autowired
    private AuthenticationManager authenticationmanager;

    @Autowired
    private myUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwttokenutil;
    
    @Autowired
    private UserInterface userInterface;
    
    @Autowired
    private UserRepository repository;
    
     @Autowired
    private FileStorageService fileStorageService;

     @Autowired
     private UserServiceImpl user;

    private static final Logger logger = LoggerFactory.getLogger(FirstController.class);
    

      
    
    // GET API AUTHENTICATION AND LOGIN
       @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody User authenticationRequest) throws Exception {
        try {
            authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhonenumber(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Wrong credentials", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getPhonenumber());
           if (userDetails == null) {
               throw new RuntimeException("User does not exist.");
           }


          User user = userInterface.signIn(authenticationRequest.getPhonenumber(),authenticationRequest.getPassword());
           if (user == null){
               String jwt = null;
               return ResponseEntity.ok(new AuthenticationResponse(jwt,user,"true"));
           }
           user.setPassword(null);
           user.setPasswordupdatetoken(null);
           user.setPasswordgentokentime(null);
           user.setOtpgenerationtime(null);
           user.setToken(null);


           try{
               ObjectMapper mapper = new ObjectMapper();
               logger.info(mapper.writeValueAsString(userDetails));
           }catch (Exception e){

           }
        final String jwt = jwttokenutil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt,user,"false"));
    }



    
    
    // REGISTER NEW USER
    @PostMapping("/registeruser")
    public ApiResponse<User> registerUser(@RequestBody User user){
    ApiResponse<User> u = new ApiResponse<>();
    u.setResponse(userInterface.registerUser(user));
    u.setMessage("Success");
    u.setStatus(HttpStatus.CREATED);
    u.setResponsecode("00");
    return u;
    }




    // UPDATE USER PROFILE
    @PutMapping("/updateprofile")
    public ApiResponse<String> updateUserProfile(@RequestBody User user){
    ApiResponse<String> u = new ApiResponse<>();
    u.setResponse(userInterface.updateUserNextOfKin(user.getKinname(),user.getKinphonenumber(),user.getKinemail(),user.getKinaddress(),user.getPhonenumber()));
    u.setStatus(HttpStatus.OK);
    u.setMessage("Success");
    u.setResponsecode("00");
    return u;
    }
    
    
    // USER SEARCH FOR TRIPS
      @PostMapping("/findtrips")
      public ApiResponse<List<Trips>> findTrips(@RequestBody Trips trip){
      ApiResponse<List<Trips>> u = new ApiResponse<>();
      u.setResponse(userInterface.findTrips(trip.getDeparture(),trip.getDestination(),trip.getDate()));
      u.setStatus(HttpStatus.OK);
      u.setMessage("Success");
      u.setResponsecode("00");
      return u;
      }

      
      // USER BOOK A TRIP
      @PostMapping("/booktrips/{id}")
      public ApiResponse<String> bookTrips(@RequestBody ObjectNode tripDetails, @PathVariable("id") long id){
      ApiResponse<String> u = new ApiResponse<>();
      String phonenumber = tripDetails.get("phonenumber").asText();
      String fullname = tripDetails.get("fullname").asText();
      String paymentreferenceid = tripDetails.get("paymentreferenceid").asText();
      JsonNode objectNumberOfSeats = tripDetails.get("numberofseats");
      int numberOfSeats = objectNumberOfSeats.asInt();
      JsonNode objectCoRider = tripDetails.get("corider");
      String isUserGoing = tripDetails.get("usergoing").asText();
      if(numberOfSeats == 0 && objectCoRider != null){ throw new RuntimeException("Please supply correct details"); }
          if(isUserGoing.contains("true")) {
              TripBooking bookTrips = userInterface.bookTrips(id, phonenumber, numberOfSeats, paymentreferenceid,fullname);
              u.setResponse("Trip Details Successfully Registered");
          } else if(isUserGoing.contains("false") && numberOfSeats > 0){ u.setResponse("Extra-Riders Successfully Registered Only"); }
                 if(numberOfSeats > 0)
                    if(objectCoRider == null){throw new RuntimeException("Please supply details for extra riders");}
           try {
               for (int i = 0; i <= numberOfSeats; i++) {
               JsonNode riderNames = objectCoRider.get(i).get("coridername");
               JsonNode riderPhoneNumber = objectCoRider.get(i).get("coriderphonenumber");
               String coRiderName = riderNames.asText();
               String coRiderPhonenumber = riderPhoneNumber.asText();
               userInterface.registerCoRiders(coRiderName,coRiderPhonenumber,phonenumber);
               }
               } catch (NullPointerException n){}
      u.setStatus(HttpStatus.OK);
      u.setMessage("Success");
      u.setResponsecode("00");
      return u;
      }


      // ADMIN TO DELETE A USER
      @DeleteMapping("/deleteuser")
      public ApiResponse<String> deleteUser(@RequestBody User user){
      ApiResponse<String> u = new ApiResponse<>();
      repository.deleteById(user.getId());
      u.setMessage("success");
      u.setStatus(HttpStatus.GONE);
      u.setResponse("User deleted successfully");
      u.setResponsecode("00");
      return u;
      }
      
    // ADMIN TO CREATE TRIPS FOR USERS TO BOOK FROM
      @PostMapping("/createtrips")
      public ApiResponse<Trips> adminCreateTripsForUsers(@RequestBody Trips trip){
      ApiResponse<Trips> u = new ApiResponse<>();
      u.setResponse(userInterface.adminToCreateTripsForBooking(trip));
      u.setStatus(HttpStatus.OK);
      u.setMessage("success");
      u.setResponsecode("00");
      return u;
      }



      
      // SEARCH FOR PASSENGER 
      @PostMapping("/searchpassenger")
      public ApiResponse<TripBooking> searchPassengerOnTripBus(@RequestBody TripBooking search){
      ApiResponse<TripBooking> u = new ApiResponse<>();
      u.setResponse(userInterface.searchPassengerOnTrip(search));
      u.setStatus(HttpStatus.OK);
      u.setMessage("success");
      u.setResponsecode("00");
      return u;
      }
    


      // GET ALL PASSENGERS ON A TRIP BUS
      @PostMapping("/getallpassengersontrip")
      public ApiResponse<List<TripBooking>> getAllPassengersOnATripBus(@RequestBody TripBooking passengers){
      ApiResponse<List<TripBooking>> u = new ApiResponse<>();
       u.setResponse(userInterface.getAllPassengersOnATrip(passengers));
       u.setStatus(HttpStatus.OK);
       u.setMessage("success");
       u.setResponsecode("00");
       return u;
      }


      
      // UPLOAD MANIFEST FILE FOR INSURANCE COMPANY
     @PutMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("vehiclenumber") String vehiclenumber) {
        String fileName = fileStorageService.storeFile(file);

        String filedownloaduri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


         logger.info("fileDownloadUri " + filedownloaduri);
         logger.info("vehicleNumber == " + vehiclenumber);

          int getFileUpdateCount = userInterface.saveFileUploadPathToDatabase(filedownloaduri, vehiclenumber);
         logger.info("getFileUpdateCount == " + getFileUpdateCount);
        return new Response(fileName, filedownloaduri,
                file.getContentType(), file.getSize());
    }




    
    // DOWNLOAD MANIFEST FILE 
    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity < Resource > downloadFile(@PathVariable String fileName, HttpServletRequest request) throws FileNotFoundException {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
      




    // UPDATE USER PASSWORD
    @PutMapping("/sendTokenToUpdatePassword")
    public ApiResponse<String> sendTokenToUpdatePassword(@RequestBody User user){
           ApiResponse<String> u = new ApiResponse<>();
           u.setResponse(userInterface.sendTokenToUpdatePassword(user.getPhonenumber()));
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           u.setResponsecode("00");
           return u;
    }



    @PostMapping("/verifyTokenForUpdatePassword")
    public ApiResponse<String> confirmTokenAndResetPassword(@RequestBody User user){
           ApiResponse<String> u = new ApiResponse<>();
           u.setResponse(userInterface.confirmPasswordResetToken(user.getPasswordupdatetoken(),user.getPhonenumber()));
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           u.setResponsecode("00");
           return u;
    }


    @PutMapping("/changepassword")
    public ApiResponse<String> updateUserPassword(@RequestBody User user){
           ApiResponse<String> u = new ApiResponse<>();
           u.setResponse(userInterface.updatePassword(user.getPassword(),user.getPhonenumber()));
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           u.setResponsecode("00");
           return u;
    }



    // GET TOTAL AMOUNT OF REGISTERED USERS OR GET ALL USERS FROM THE APPLICATION
    @GetMapping("/getTotalUsers")
    public ApiResponse<Integer> getTotalUsersOnPlatform(){
           ApiResponse<Integer> u = new ApiResponse<>();
           u.setResponse(userInterface.getTotalAmountOfRegisteredUsers());
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           u.setResponsecode("00");
           return u;
}


    // ENDPOINT TO CREATE BUS STATION ADMIN
    @PostMapping("/createBusStationAdmin")
    public ApiResponse<User> createBusStationAdmin(@RequestBody User user){
           ApiResponse<User> u = new ApiResponse<>();
           u.setResponse(userInterface.createBusStationAdmin(user));
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           u.setResponsecode("00");
           return u;
    }



    @GetMapping("/getListOfAllUser")
    public ApiResponse<List<User>> getListOfAllUsers(){
           ApiResponse<List<User>> u = new ApiResponse<>();
           u.setResponse(userInterface.getListOfAllUsers());
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           u.setResponsecode("00");
        return u;
    }




        @PutMapping("/verifyUserOTP")
        public ApiResponse<String> otpVerification(@RequestBody User user){
            ApiResponse<String> u = new ApiResponse<>();
            u.setResponse(userInterface.verifyTokenForUserAuthentication(user.getToken(),user.getPhonenumber()));
            u.setStatus(HttpStatus.OK);
            u.setMessage("success");
            u.setResponsecode("00");
            return u;
        }




        @PostMapping("/getListOfUserBasedOnPrice")
        public ApiResponse<List<Trips>> getListOfTripsBasedOnPrice(@RequestBody Trips trip){
           ApiResponse<List<Trips>> u = new ApiResponse<>();
           u.setResponse(userInterface.getListOfTripsBasedOnPriceInDescOrder(trip.getDeparture(),trip.getDestination(),trip.getDate()));
            u.setStatus(HttpStatus.OK);
            u.setMessage("success");
            u.setResponsecode("00");
            return u;
        }


        @PostMapping("/getFilteredListForUserSearch")
        public ApiResponse<List<Trips>> getFilteredListForUserSearch(@RequestBody Trips trip){
           ApiResponse<List<Trips>> a = new ApiResponse<>();
           a.setResponse(userInterface.getAllTripsFilteredbyUser(trip.getPrice(),trip.getDeparture(),trip.getDestination()));
            a.setStatus(HttpStatus.OK);
            a.setMessage("success");
            a.setResponsecode("00");
            return a;
        }


        @PostMapping("/getUserProfile")
        public ApiResponse<User> getUserProfile(@RequestBody User user){
            ApiResponse<User> u = new ApiResponse<>();
            u.setResponse(userInterface.getUserProfile(user.getPhonenumber()));
            u.setStatus(HttpStatus.OK);
            u.setMessage("success");
            u.setResponsecode("00");
            return u;
        }

        // GET EXACT BUS FILE MANIFEST AND DOWNLOAD
            @PostMapping("/getFileManifestToDownload")
            public ApiResponse<String> getFileManifestToDownload(@RequestBody Trips trip){
                ApiResponse<String> u = new ApiResponse<>();
                u.setResponse(userInterface.getManifestFileToDownload(trip.getDeparture(),trip.getDestination(),
                        trip.getDate(),trip.getVehiclenumber(),trip.getTransportcompany()));
                u.setStatus(HttpStatus.OK);
                u.setMessage("success");
                u.setResponsecode("00");
                return u;
            }


            // ADMIN TO DELETE USER
                @DeleteMapping("/adminToDeleteUser/{id}")
                public ApiResponse<Long> deleteUserForAdmin(@PathVariable long id){
                    ApiResponse<Long> u = new ApiResponse<>();
                    u.setResponse(userInterface.deleteMobileUserAccountByAdmin(id));
                    u.setStatus(HttpStatus.OK);
                    u.setMessage("success");
                    u.setResponsecode("00");
                    return u;
                }

                @PostMapping("/orderTripsBasedonPriceInAscendingOrder")
                public ApiResponse<List<Trips>> getTripsBasedOnPriceInAscendingOrder(@RequestBody Trips trip){
                    ApiResponse<List<Trips>> u = new ApiResponse<>();
                    u.setResponse(userInterface.getListOfTripsBasedOnPriceInAscendingOrder(trip.getDeparture(),trip.getDestination(),trip.getDate()));
                    u.setStatus(HttpStatus.OK);
                    u.setMessage("success");
                    u.setResponsecode("00");
                    return u;
                }


                @PostMapping("/getTripDetailsUsingId")
                public ApiResponse<Trips> getTripDetailsUsingId(@RequestBody Trips trips){
                    ApiResponse<Trips> u = new ApiResponse<>();
                    u.setResponse(userInterface.getTripDetailsUsingId(trips.getId()));
                    u.setStatus(HttpStatus.OK);
                    u.setMessage("success");
                    u.setResponsecode("00");
                    return u;
                }


                @PostMapping("/getBookedTripsByPhonenumber")
                public ApiResponse<List<TripBooking>> getBookedTripsForEachUser(@RequestBody TripBooking trip){
                    ApiResponse<List<TripBooking>> u = new ApiResponse<>();
                    u.setResponse(userInterface.getBookedTrips(trip.getPhonenumber()));
                    u.setStatus(HttpStatus.OK);
                    u.setMessage("success");
                    u.setResponsecode("00");
                    return u;
                }


    @RequestMapping(value = "/adminLogIn", method = RequestMethod.POST)
    public ResponseEntity<?> adminLogIn(@RequestBody User authenticationRequest) throws Exception {
        try {
            authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhonenumber(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Wrong credentials", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getPhonenumber());
        if (userDetails == null) {
            throw new RuntimeException("User does not exist.");
        }

        User user = userInterface.adminSignIn(authenticationRequest.getPhonenumber(),authenticationRequest.getPassword());
        user.setPassword(null);
        user.setPasswordupdatetoken(null);
        user.setPasswordgentokentime(null);
        user.setOtpgenerationtime(null);
        user.setToken(null);

        try{
            ObjectMapper mapper = new ObjectMapper();
            logger.info(mapper.writeValueAsString(userDetails));
        }catch (Exception e){

        }
        final String jwt = jwttokenutil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt,user,"N/A"));
    }




}
