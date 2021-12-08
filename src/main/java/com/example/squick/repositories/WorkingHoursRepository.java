package com.example.squick.repositories;

import com.example.squick.models.WorkingHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkingHoursRepository extends JpaRepository<WorkingHours, Long> {

}
