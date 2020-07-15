/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insurance.travel.repository;

import com.insurance.travel.model.Trips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author oreoluwa
 */
@Repository
public interface TripsRepository extends JpaRepository<Trips, Long>{


    List<Trips> findByDepartureAndDestinationAndDate(String departure, String destination, Date date);

    Trips findById(long id);

    @Modifying
    @Transactional
    @Query("update Trips t set t.filedownloaduri = :filedownloaduri where vehiclenumber = :vehicleNumber")
    int uploadManifestWithTripBus(@Param("filedownloaduri") String filedownloaduri, @Param("vehicleNumber") String vehicleNumber);


    List<Trips> findAllByDepartureAndDestinationAndDateOrderByPriceDesc(String departure, String destination, Date date);

    List<Trips> findAllByDepartureAndDestinationAndDateOrderByPriceAsc(String departure, String destination, Date date);

    @Query("select t from Trips t where t.price <= :price and t.departure = :departure and t.destination = :destination")
    List<Trips> getAllFilteredTrips(@Param("price")BigDecimal price,@Param("departure") String departure, @Param("destination") String destination);

    @Query("select t.filedownloaduri from Trips  t where t.departure = :departure and t.destination = :destination and t.date = :date and t.vehiclenumber = :vehiclenumber and t.transportcompany = :transportcompany")
    String getTripFileManifestToDownload(@Param("departure") String departure, @Param("destination") String destination,
                                         @Param("date") Date date, @Param("vehiclenumber") String vehiclenumber,
                                         @Param("transportcompany") String transportcompany);

}
