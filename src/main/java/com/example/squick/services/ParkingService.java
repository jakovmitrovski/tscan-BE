package com.example.squick.services;

import com.example.squick.models.Parking;
import com.example.squick.models.dtos.ParkingDto;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;

import java.util.List;
import java.util.Optional;

public interface ParkingService {

    List<Parking> findAllExplore(Integer priceFrom, Integer priceTo, boolean openNow, boolean freeSpaces, String keyword);

    Optional<ExploreParkingDetailsProjection> findByIdExplore(Long id);

    Optional<Boolean> create(ParkingDto parkingDto);

    Optional<Boolean> edit(ParkingDto parkingDto, Long id);

    Optional<Boolean> delete(Long id);
}
