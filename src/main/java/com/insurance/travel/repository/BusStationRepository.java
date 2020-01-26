package com.insurance.travel.repository;

import com.insurance.travel.model.BusStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStationRepository extends JpaRepository<BusStation,Long> {



}
