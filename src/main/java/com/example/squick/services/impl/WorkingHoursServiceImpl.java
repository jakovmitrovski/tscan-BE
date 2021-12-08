package com.example.squick.services.impl;

import com.example.squick.models.WorkingHours;
import com.example.squick.models.dtos.WorkingHoursDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.repositories.WorkingHoursRepository;
import com.example.squick.services.WorkingHoursService;
import com.example.squick.utils.Constants;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    private final WorkingHoursRepository workingHoursRepository;

    public WorkingHoursServiceImpl(WorkingHoursRepository workingHoursRepository) {
        this.workingHoursRepository = workingHoursRepository;
    }

    @Override
    public List<WorkingHours> findAllByParkingId(Long parkingId) {
        return workingHoursRepository.findAllByParkingId(parkingId);
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
    public Optional<Boolean> edit(WorkingHours workingHours, Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> createWorkingHoursForParking(WorkingHoursDto workingHours, Long parkingId) {
        return Optional.empty();
    }
}
