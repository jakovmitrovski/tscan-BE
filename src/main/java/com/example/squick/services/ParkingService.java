package com.example.squick.services;

import com.example.squick.models.dtos.ParkingDto;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import com.example.squick.models.projections.ExploreParkingProjection;
import com.example.squick.models.projections.MapParkingProjection;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ParkingService {

    List<MapParkingProjection> findAllMap();

    Page<ExploreParkingProjection> findAllExplore();

    Optional<ExploreParkingDetailsProjection> findByIdExplore(Long id);

    Optional<Boolean> create(ParkingDto parkingDto);

    Optional<Boolean> edit(ParkingDto parkingDto, Long id);

    Optional<Boolean> delete(Long id);


}
