package com.insurance.travel.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.insurance.travel.dtos.BookTripForUserDTO;
import com.insurance.travel.dtos.TripBookingDTO;
import com.insurance.travel.fileupload.FileStorageService;
import com.insurance.travel.fileupload.Response;
import com.insurance.travel.model.*;
import com.insurance.travel.repository.UserRepository;
import com.insurance.travel.response.ApiResponse;
import com.insurance.travel.service.SupportingUserServiceInterface;
import com.insurance.travel.service.UserInterface;
import com.insurance.travel.service.UserServiceImpl;
import com.insurance.travel.service.myUserDetailsService;
import com.insurance.travel.tokenConfig.JwtUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.sun.net.httpserver.HttpsConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
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
public class SupportingController {

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

    @Autowired
    private SupportingUserServiceInterface supportingUserServiceInterface;

    private static final Logger logger = LoggerFactory.getLogger(FirstController.class);



    @PostMapping("/resendOtpForUserAuthentication")
    public ApiResponse<String> resendTokenForAuthentication(@RequestBody User user){
        ApiResponse<String> u = new ApiResponse<>();
        u.setResponse(userInterface.resendOtpTokenForAuthentication(user.getPhonenumber()));
        u.setStatus(HttpStatus.OK);
        u.setResponsecode("00");
        u.setMessage("success");
        return u;
    }


    @RequestMapping(value = "/busAdminLogin", method = RequestMethod.POST)
    public ResponseEntity<?> busAdminLogIn(@RequestBody User authenticationRequest) throws Exception {
        try {
            authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getPhonenumber(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Wrong credentials", e);
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getPhonenumber());
        if (userDetails == null) {
            throw new RuntimeException("User does not exist.");
        }

        User user = userInterface.busAgentAdminSignIn(authenticationRequest.getPhonenumber(),authenticationRequest.getPassword());
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


    @PostMapping("/updateBusAdminPassword")
    public ApiResponse<User> updateBusAdminPassword(@RequestBody User user){
        ApiResponse<User> u = new ApiResponse<>();
        u.setResponse(userInterface.updateBusAdminPassword(user.getPhonenumber(),user.getPassword()));
        u.setStatus(HttpStatus.OK);
        u.setResponsecode("00");
        u.setMessage("success");
        return u;
    }

    @PostMapping("/adminCreateBusStations")
    public ApiResponse<String> adminCreateBusStation(@RequestBody BusStation user){
        ApiResponse<String> u = new ApiResponse<>();
        u.setResponse(supportingUserServiceInterface.adminCreateBusStations(user.getStation(),user.getState(),user.getAddress()));
        u.setStatus(HttpStatus.OK);
        u.setMessage("success");
        u.setResponsecode("00");
        return u;
    }

    @GetMapping("/listOfAllBusStationsDetails")
    public ApiResponse<List<BusStation>> getListOfAllBusStationDetails(){
        ApiResponse<List<BusStation>> u = new ApiResponse<>();
        u.setResponse(supportingUserServiceInterface.getAllListOfBusStations());
        u.setStatus(HttpStatus.OK);
        u.setResponsecode("00");
        u.setMessage("success");
        return u;
    }

    @PostMapping("/busadminassignusertobus")
    public ApiResponse<TripBooking> busAdminregisterUsertoBus(@RequestBody BookTripForUserDTO bookTripForUserDTO){
        ApiResponse<TripBooking> u = new ApiResponse<>();
        u.setResponse(supportingUserServiceInterface.busAdminAssignUserToBus(bookTripForUserDTO));
        u.setMessage("success");
        u.setResponsecode("00");
        u.setStatus(HttpStatus.OK);
        return u;
    }

    @PostMapping("/getistofallbusadminusers")
    public ApiResponse<List<User>> getListOfAllUsers(){
        ApiResponse<List<User>> u = new ApiResponse<>();
        u.setResponse(supportingUserServiceInterface.getListOfAllBusAdminUsers());
        u.setResponsecode("00");
        u.setStatus(HttpStatus.OK);
        u.setMessage("success");
        return u;
    }

    @PostMapping("/getlistofallcreatedtrips")
    public ApiResponse<List<Trips>> getListOfAllCreatedTrips(){
        ApiResponse<List<Trips>> u = new ApiResponse<>();
        u.setResponse(supportingUserServiceInterface.getListOfAllTrips());
        u.setMessage("success");
        u.setStatus(HttpStatus.OK);
        u.setResponsecode("00");
        return u;
    }



    @PostMapping("/getlistofbookedtripsbydate")
    public ApiResponse<List<TripBooking>> getListOfBookedtripsByDate(@RequestBody TripBookingDTO date){
        ApiResponse<List<TripBooking>> u = new ApiResponse<>();
        u.setResponse(supportingUserServiceInterface.getListOfBookedTripsForAdate(date.getDeparturedate()));
        u.setCount(supportingUserServiceInterface.getCountOfbookedtrips(date.getDeparturedate()));
        u.setMessage("success");
        u.setResponsecode("00");
        u.setStatus(HttpStatus.OK);
        return u;
    }

}
