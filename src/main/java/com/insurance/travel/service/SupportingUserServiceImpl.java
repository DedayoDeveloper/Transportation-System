package com.insurance.travel.service;

import com.insurance.travel.dtos.BookTripForUserDTO;
import com.insurance.travel.model.BusStation;
import com.insurance.travel.model.TripBooking;
import com.insurance.travel.model.Trips;
import com.insurance.travel.model.User;
import com.insurance.travel.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class SupportingUserServiceImpl implements SupportingUserServiceInterface {



    @Autowired
    private UserRepository repository;

    @Autowired
    private TripsRepository tripsrepository;

    @Autowired
    private TripBookingRepository tripbookrepo;

    @Autowired
    private CoRiderRepository coRiders;

    @Autowired
    private BusStationRepository busStationRepository;

    @Autowired
    @Qualifier("bCryptPasswordEncoder")
    private PasswordEncoder passwordencoder;


    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public String adminCreateBusStations(String station,String state,String address){
        BusStation newBusStation = new BusStation();
        newBusStation.setStation(station);
        newBusStation.setState(state);
        newBusStation.setAddress(address);
        busStationRepository.save(newBusStation);
        return "Station successfully created";
    }

    @Override
    public List<BusStation> getAllListOfBusStations(){
        List<BusStation> getFullList = busStationRepository.findAll();
        return getFullList;
    }



    @Override
    public TripBooking busAdminAssignUserToBus(BookTripForUserDTO bookTripForUserDTO){
        TripBooking tripBooking = new TripBooking();
        tripBooking.setFullname(bookTripForUserDTO.getFullname());
        tripBooking.setPhonenumber(bookTripForUserDTO.getPhonenumber());
        tripBooking.setDeparture(bookTripForUserDTO.getDeparture());
        tripBooking.setDestination(bookTripForUserDTO.getDestination());
        tripBooking.setDeparturedate(bookTripForUserDTO.getDeparturedate());
        tripbookrepo.save(tripBooking);
        return tripBooking;

    }

    @Override
    public List<User> getListOfAllBusAdminUsers(){
        String role = "ROLE_BUSADMIN";
        List<User> getListOfAllBusAdminUsers = repository.findAllByRole(role);
        logger.info("busadmins {}", getListOfAllBusAdminUsers);
        return getListOfAllBusAdminUsers;
    }


    @Override
    public List<Trips> getListOfAllTrips(){
        List<Trips> getListOfAllTrips = tripsrepository.findAll();
        return getListOfAllTrips;
    }


    @Override
    public List<TripBooking> getListOfBookedTripsForAdate(Date date){
        logger.info("date = {}", date);
//        String newdate = "2020-01-21";
        List<TripBooking> getBookedTrips = tripbookrepo.findAllByDeparturedate(date);
        logger.info("list of trips per date {}",getBookedTrips);
        return getBookedTrips;
    }

    @Override
    public int getCountOfbookedtrips(Date date){
        int getCount = tripbookrepo.CountAllByDate(date);
        return getCount;
    }
}
