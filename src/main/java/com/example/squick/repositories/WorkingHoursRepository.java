package com.example.squick.repositories;

import com.example.squick.models.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {

    @Query(value = "select Parking.workingHours from Parking where Parking .id=:parkingId", nativeQuery = true)
    List<WorkingHours> findAllByParkingId(Long parkingId);
}
