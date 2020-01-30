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
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author oreoluwa
 */
@Repository
public interface TripsRepository extends JpaRepository<Trips, Long>{


    List<Trips> findByDepartureAndDestinationAndDate(String departure,String destination,String date);

    @Modifying
    @Transactional
    @Query("update Trips t set t.filedownloaduri = :filedownloaduri where vehiclenumber = :vehicleNumber")
    int uploadManifestWithTripBus(@Param("filedownloaduri") String filedownloaduri, @Param("vehicleNumber") String vehicleNumber);


    List<Trips> findAllByDepartureAndDestinationAndDateOrderByPriceDesc(String departure, String destination, String date);

    List<Trips> findAllByDepartureAndDestinationAndDateOrderByPriceAsc(String departure, String destination, String date);

    @Query("select t from Trips t where t.price <= :price and t.departurepark = :departurepark and t.arrivalpark = :arrivalpark and t.time = :time")
    List<Trips> getAllFilteredTrips(@Param("price")BigDecimal price,@Param("departurepark") String departurepark, @Param("arrivalpark") String arrivalpark,@Param("time") String time);

    @Query("select t.filedownloaduri from Trips  t where t.departure = :departure and t.destination = :destination and t.date = :date and t.vehiclenumber = :vehiclenumber and t.transportcompany = :transportcompany")
    String getTripFileManifestToDownload(@Param("departure") String departure,@Param("destination") String destination,
                                         @Param("date") String date,@Param("vehiclenumber") String vehiclenumber,
                                         @Param("transportcompany") String transportcompany);

}
