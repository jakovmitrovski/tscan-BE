package com.example.squick.services.impl;

import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import com.example.squick.models.projections.ExploreParkingProjection;
import com.example.squick.models.projections.MapParkingProjection;
import com.example.squick.repositories.ParkingRepository;
import com.example.squick.repositories.WorkingHoursRepository;
import com.example.squick.services.ParkingService;
import com.example.squick.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements ParkingService {

    private final ParkingRepository parkingRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<MapParkingProjection> findAllMap() {
        return this.parkingRepository.findAllMap();
    }

    @Override
    public Page<ExploreParkingProjection> findAllExplore() {
        return this.parkingRepository.findAllExplore();
    }

    @Override
    public Optional<ExploreParkingDetailsProjection> findByIdExplore(Long id) {
        return Optional.of(
                this.parkingRepository.findParkingById(id)
                        .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage))
        );
    }
}
