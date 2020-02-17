/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.repository;

import com.insurance.travel.model.TripBooking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author oreoluwa
 */
@Repository
public interface TripBookingRepository extends JpaRepository<TripBooking, Long>{

    List<TripBooking> findByPhonenumber(String phonenumber);
    
    @Query("select t from TripBooking t where phonenumber = :phonenumber")
    TripBooking getPassengerRegisteredForTrip(@Param("phonenumber") String phonenumber);
    
    @Query("select t from TripBooking t where t.transportcompany = :transportcompany and t.destination = :destination")
    List<TripBooking> getAllPassengersOnATrip(@Param("transportcompany") String transportcompany, @Param("destination") String destination);
    
}
