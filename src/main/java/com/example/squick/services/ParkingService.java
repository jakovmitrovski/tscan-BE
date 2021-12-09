package com.example.squick.services;

import com.example.squick.models.Parking;
import com.example.squick.models.dtos.ParkingDto;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ParkingService {

    List<Parking> findAllMap();

    Page<Parking> findAllExplore(Integer start, Integer items, Integer priceFrom, Integer priceTo, boolean openNow, String keyword);

    Optional<ExploreParkingDetailsProjection> findByIdExplore(Long id);

    Optional<Boolean> create(ParkingDto parkingDto);

    Optional<Boolean> edit(ParkingDto parkingDto, Long id);

    Optional<Boolean> delete(Long id);

    Page<Parking> findAll(Integer start, Integer items);


}
