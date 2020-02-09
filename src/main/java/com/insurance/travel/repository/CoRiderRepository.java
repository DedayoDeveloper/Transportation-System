package com.insurance.travel.repository;


import com.insurance.travel.model.CoRiders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoRiderRepository extends JpaRepository<CoRiders, Long> {
}
