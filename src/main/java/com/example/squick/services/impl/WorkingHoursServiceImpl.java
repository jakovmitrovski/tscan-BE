package com.example.squick.services.impl;

import com.example.squick.models.Parking;
import com.example.squick.models.WorkingHours;
import com.example.squick.models.dtos.WorkingHoursDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.repositories.ParkingRepository;
import com.example.squick.repositories.WorkingHoursRepository;
import com.example.squick.services.WorkingHoursService;
import com.example.squick.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    private final WorkingHoursRepository workingHoursRepository;
    private final ParkingRepository parkingRepository;

    public WorkingHoursServiceImpl(WorkingHoursRepository workingHoursRepository, ParkingRepository parkingRepository) {
        this.workingHoursRepository = workingHoursRepository;
        this.parkingRepository = parkingRepository;
    }

    @Override
    public List<WorkingHours> findAllWorkingHoursForParking(Long parkingId) {
        Parking parking = parkingRepository.findById(parkingId).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
        return parking.getWorkingHours();
    }

    @Override
    public Optional<WorkingHours> findById(Long id) {
        return workingHoursRepository.findById(id);
    }

    @Override
    public Optional<Boolean> delete(Long id) {
        workingHoursRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.workHoursNotFound));

        try {
            workingHoursRepository.deleteById(id);
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }

    }

    @Override
    public Optional<Boolean> edit(WorkingHoursDto obj, Long id, Long parkingId) {

        WorkingHours workingHours = workingHoursRepository.findById(id).orElseThrow(() -> new CustomNotFoundException(Constants.workHoursNotFound));

        ValidateWorkingHours(obj);

        //Check if there are working hours for that particular day that have an id different from the one provided
        Parking parking = parkingRepository.findById(parkingId).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
        List<WorkingHours> parkingWorkingHours = parking.getWorkingHours();

        for (WorkingHours hours:parkingWorkingHours) {
            if(hours.getDayOfWeek().equals(obj.getDayOfWeek())){
                if(!hours.getId().equals(id))
                    throw new BadRequestException(Constants.workingHoursAlreadyExist);
            }
        }

        try {
            workingHours.setDayOfWeek(obj.getDayOfWeek());
            workingHours.setTimeFrom(obj.getTimeFrom());
            workingHours.setTimeTo(obj.getTimeTo());
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    @Override
    public Optional<Boolean> createWorkingHoursForParking(WorkingHoursDto workingHours, Long parkingId) {

        ValidateWorkingHours(workingHours);

        //Check if there are working hours for that day. If there are dont add new working hours
        Parking parking = parkingRepository.findById(parkingId).orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
        List<WorkingHours> parkingWorkingHours = parking.getWorkingHours();

        for (WorkingHours hours:parkingWorkingHours) {
            if(hours.getDayOfWeek().equals(workingHours.getDayOfWeek())){
                throw new BadRequestException(Constants.workingHoursAlreadyExist);
            }
        }

        try {
            workingHoursRepository.save(new WorkingHours(workingHours.getTimeFrom(), workingHours.getTimeTo(), workingHours.getDayOfWeek()));
            return Optional.of(true);
        } catch (Exception exception) {
            throw new BadRequestException(Constants.badRequest);
        }
    }

    public void ValidateWorkingHours(WorkingHoursDto workingHours) {
        if (workingHours.getTimeFrom().isAfter(workingHours.getTimeTo()))
            throw new BadRequestException(Constants.invalidWorkingHoursTimes);

        if (workingHours.getDayOfWeek().getValue() < 0 || workingHours.getDayOfWeek().getValue() > 6)
            throw new BadRequestException(Constants.badRequest);
    }
}
