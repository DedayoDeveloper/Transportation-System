/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.repository;

import com.insurance.travel.model.Trips;
import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 *
 * @author oreoluwa
 */
@Repository
public interface TripsRepository extends JpaRepository<Trips, Long>{
   
    @Query("select t from Trips t where t.departure = :departure and t.destination = :destination and date = :date")
    Trips getTripsAvialable(@Param("departure") String departure,@Param("destination") String destination, @Param("date") String date);

    @Modifying
    @Transactional
    @Query("update Trips t set t.filedownloaduri = :filedownloaduri where vehiclenumber = :vehicleNumber")
    int uploadManifestWithTripBus(@Param("filedownloaduri") String filedownloaduri, @Param("vehicleNumber") String vehicleNumber);


}
