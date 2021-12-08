package com.example.squick.services;

import com.example.squick.models.WorkingHours;
import com.example.squick.models.dtos.WorkingHoursDto;

import java.util.List;
import java.util.Optional;

public interface WorkingHoursService {

    List<WorkingHours> findAllByParkingId(Long parkingId);

    Optional<WorkingHours> findById(Long id);

    Optional<Boolean> delete(Long id);

    Optional<Boolean> edit(WorkingHours workingHours, Long id);

    Optional<Boolean> createWorkingHoursForParking(WorkingHoursDto workingHours, Long parkingId);
}
