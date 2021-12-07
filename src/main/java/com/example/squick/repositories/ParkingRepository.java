package com.example.squick.repositories;

import com.example.squick.models.Parking;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import com.example.squick.models.projections.ExploreParkingProjection;
import com.example.squick.models.projections.MapParkingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Page<ExploreParkingProjection> findAllExplore();
    List<MapParkingProjection> findAllMap();
    Optional<ExploreParkingDetailsProjection> findParkingById(Long id);
}
