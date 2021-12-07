package com.example.squick.services.impl;

import com.example.squick.repositories.WorkingHoursRepository;
import com.example.squick.services.WorkingHoursService;
import org.springframework.stereotype.Service;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {

    private final WorkingHoursRepository workingHoursRepository;

    public WorkingHoursServiceImpl(WorkingHoursRepository workingHoursRepository) {
        this.workingHoursRepository = workingHoursRepository;
    }
}
