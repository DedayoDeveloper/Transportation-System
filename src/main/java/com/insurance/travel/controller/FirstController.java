/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.controller;

import com.insurance.travel.fileupload.FileStorageService;
import com.insurance.travel.fileupload.Response;
import com.insurance.travel.model.AuthenticationRequest;
import com.insurance.travel.model.AuthenticationResponse;
import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;
import com.insurance.travel.repository.TripsRepository;
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
 * @author oreoluwa
 */
@RestController
public class FirstController {
    
    
     @Autowired
    private AuthenticationManager authenticationmanager;

    @Autowired
    private myUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtil jwttokenutil;
    
    @Autowired
    private UserInterface userinterface;
    
    @Autowired
    private UserRepository repository;
    
     @Autowired
    private FileStorageService fileStorageService;

     @Autowired
     private UserServiceImpl user;


    
     
     
    private static final Logger logger = LoggerFactory.getLogger(FirstController.class);
    
    // THIS IS THE CONTROLLER METHOD FOR A CUSTOMER USER
      
    
    // GET API AUTHENTICATION
       @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("incorrect username and password", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwttokenutil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
    
    
    
    // REGISTER NEW USER
    @PostMapping("/registeruser")
    public ApiResponse<User> registerUser(@RequestBody User user){
    ApiResponse<User> u = new ApiResponse<>();
    u.setResponse(userinterface.registerUser(user));
    u.setMessage("Success");
    u.setStatus(HttpStatus.CREATED);
    return u;
    }
    
    
  // USER SIGN IN
    @PostMapping("/login")
    public ApiResponse<User> userLogin(@RequestBody User user){
        ApiResponse<User> u = new ApiResponse<>();
        u.setResponse(userinterface.signIn(user.getPhonenumber(), user.getPassword()));
        u.setMessage("Success");
        u.setStatus(HttpStatus.OK);
        return u;
    }
    
    
    
    // UPDATE USER PROFILE

    @PutMapping("/updateprofile")
    public ApiResponse<String> updateUserProfile(@RequestBody User user){
    ApiResponse<String> u = new ApiResponse<>();
    u.setResponse(userinterface.updateUserNextOfKin(user.getKinname(),user.getKinphonenumber(),user.getKinemail(),user.getKinaddress(),user.getPhonenumber()));
    u.setStatus(HttpStatus.OK);
    u.setMessage("Success");
    return u;
    }
    
    
    // USER SEARCH FOR TRIPS
      @PostMapping("/findtrips")
      public ApiResponse<Trips> findTrips(@RequestBody Trips trip){
      ApiResponse<Trips> u = new ApiResponse<>();
      u.setResponse(userinterface.findTrips(trip.getDeparture(),trip.getDestination(),trip.getDate()));
      u.setStatus(HttpStatus.OK);
      u.setMessage("Success");
      return u;
      }
    
      
      // USER BOOK A TRIP
      @PostMapping("/booktrips")
      public ApiResponse<TripBooking> bookTrips(@RequestBody TripBooking trip){
      ApiResponse<TripBooking> u = new ApiResponse<>();
      u.setResponse(userinterface.bookTrips(trip));
      u.setStatus(HttpStatus.OK);
      u.setMessage("Success");
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
      return u;
      }
      
      
    // ADMIN TO CREATE TRIPS FOR USERS TO BOOK FROM
      @PostMapping("/createtrips")
      public ApiResponse<Trips> adminCreateTripsForUsers(@RequestBody Trips trip){
      ApiResponse<Trips> u = new ApiResponse<>();
      u.setResponse(userinterface.adminToCreateTripsForBooking(trip));
      u.setStatus(HttpStatus.OK);
      u.setMessage("success");
      return u;
      }


      
      // SEARCH FOR PASSENGER 
      @PostMapping("/searchpassenger")
      public ApiResponse<TripBooking> searchPassengerOnTripBus(@RequestBody TripBooking search){
      ApiResponse<TripBooking> u = new ApiResponse<>();
      u.setResponse(userinterface.searchPassengerOnTrip(search));
      u.setStatus(HttpStatus.OK);
      u.setMessage("success");
      return u;
      }
    
      
      // GET ALL PASSENGERS ON A TRIP BUS
      @PostMapping("/getallpassengersontrip")
      public ApiResponse<List<TripBooking>> getAllPassengersOnATripBus(@RequestBody TripBooking passengers){
      ApiResponse<List<TripBooking>> u = new ApiResponse<>();
       u.setResponse(userinterface.getAllPassengersOnATrip(passengers));
       u.setStatus(HttpStatus.OK);
       u.setMessage("success");
       return u;
      }
      
      
      // UPLOAD MANIFEST FILE FOR INSURANCE COMPANY
     @PutMapping("/uploadFile")
    public Response uploadFile(@RequestParam("file") MultipartFile file,@RequestParam String vehiclenumber) {
        String fileName = fileStorageService.storeFile(file);

        String filedownloaduri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();


         logger.info("fileDownloadUri " + filedownloaduri);

          int getFileUpdateCount = userinterface.saveFileUploadPathToDatabase(filedownloaduri, vehiclenumber);
         System.out.println("getFileUpdateCount == " + getFileUpdateCount);
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
    @PostMapping("/updatepassword")
    public ApiResponse<String> updateUserPassword(@RequestBody User user){
           ApiResponse<String> u = new ApiResponse<>();
           u.setResponse(userinterface.updatePassword(user.getPassword(),user.getPhonenumber()));
           u.setStatus(HttpStatus.OK);
           u.setMessage("success");
           return u;
    }







}
