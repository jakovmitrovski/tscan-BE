package com.example.squick.models.dtos;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class WorkingHoursDto {

    @DateTimeFormat(pattern = "HH:MM")
    LocalTime timeFrom;

    @DateTimeFormat(pattern = "HH:MM")
    LocalTime timeTo;

    DayOfWeek dayOfWeek;

    public WorkingHoursDto(LocalTime timeFrom, LocalTime timeTo, DayOfWeek dayOfWeek) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.dayOfWeek = dayOfWeek;
    }
}
