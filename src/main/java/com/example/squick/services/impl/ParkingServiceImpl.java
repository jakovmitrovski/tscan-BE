package com.example.squick.services.impl;

import com.example.squick.models.Parking;
import com.example.squick.models.WorkingHours;
import com.example.squick.models.dtos.ParkingDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomItemAlreadyExistsException;
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
    private final WorkingHoursRepository workingHoursRepository;

    public ParkingServiceImpl(ParkingRepository parkingRepository, WorkingHoursRepository workingHoursRepository) {
        this.parkingRepository = parkingRepository;
        this.workingHoursRepository = workingHoursRepository;
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

    private List<WorkingHours> getAllWorkingHours(List<Long> workingHoursIds) {
        return this.workingHoursRepository.findAllById(workingHoursIds);
    }


    @Override
    public Optional<Boolean> create(ParkingDto parkingDto) {
        List<WorkingHours> workingHours = this.getAllWorkingHours(parkingDto.getWorkingHoursIds());
        if (workingHours.size() != parkingDto.getWorkingHoursIds().size()) {
            throw new CustomNotFoundException(Constants.workHoursNotFound);
        }
        Optional<Parking> p = this.parkingRepository.findByName(parkingDto.getName());
        if (p.isPresent()) {
            throw new CustomItemAlreadyExistsException(Constants.parkingAlreadyExists);
        }
        try {
            Parking parking = new Parking(parkingDto, workingHours);
            this.parkingRepository.save(parking);
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    @Override
    public Optional<Boolean> edit(ParkingDto parkingDto, Long id) {

        Parking parking = this.parkingRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
        List<WorkingHours> workingHours = this.getAllWorkingHours(parkingDto.getWorkingHoursIds());
        if (workingHours.size() != parkingDto.getWorkingHoursIds().size()) {
            throw new CustomNotFoundException(Constants.workHoursNotFound);
        }

        try {
            parking.setName(parkingDto.getName());
            parking.setLocationAddress(parkingDto.getLocationAddress());
            parking.setLongitude(parkingDto.getLongitude());
            parking.setLatitude(parkingDto.getLatitude());
            parking.setHourlyPrice(parkingDto.getHourlyPrice());
            parking.setMonthlyPrice(parkingDto.getMonthlyPrice());
            parking.setYearlyPrice(parkingDto.getYearlyPrice());
            parking.setCapacity(parkingDto.getCapacity());
            parking.setNumberOfFreeSpaces(parkingDto.getNumberOfFreeSpaces());
            parking.setImageUrl(parkingDto.getImageUrl());
            parking.setWorkingHours(workingHours);
            this.parkingRepository.save(parking);
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    @Override
    public Optional<Boolean> delete(Long id) {
        Parking parking = this.parkingRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
        try {
            this.parkingRepository.delete(parking);
        } catch (Exception e) {
            throw new BadRequestException(Constants.badRequest);
        }
        return Optional.of(true);
    }
}
