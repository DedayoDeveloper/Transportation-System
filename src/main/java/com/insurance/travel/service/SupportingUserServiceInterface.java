package com.insurance.travel.service;

import com.insurance.travel.dtos.BookTripForUserDTO;
import com.insurance.travel.model.BusStation;
import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;

import java.util.Date;
import java.util.List;

public interface SupportingUserServiceInterface {

     String adminCreateBusStations(String station,String state,String address);
     List<BusStation> getAllListOfBusStations();
     TripBooking busAdminAssignUserToBus(BookTripForUserDTO bookTripForUserDTO);
    List<User> getListOfAllBusAdminUsers();
    List<Trips> getListOfAllTrips();
    List<TripBooking> getListOfBookedTripsForAdate(Date date);
    int getCountOfbookedtrips(Date date);
}
