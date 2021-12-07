package com.example.squick.services.impl;

import com.example.squick.repositories.ParkingRepository;
import com.example.squick.repositories.WorkingHoursRepository;
import com.example.squick.services.ParkingService;
import org.springframework.stereotype.Service;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;
    private final WorkingHoursRepository workingHoursRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository, WorkingHoursRepository workingHoursRepository) {
        this.parkingRepository = parkingRepository;
        this.workingHoursRepository = workingHoursRepository;
    }
}
