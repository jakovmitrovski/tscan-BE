package com.example.squick.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
public class WorkingHours {

    public WorkingHours() {
    }

    public WorkingHours(LocalTime timeFrom, LocalTime timeTo, DayOfWeek dayOfWeek) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.dayOfWeek = dayOfWeek;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @DateTimeFormat(pattern = "hh:mm")
    LocalTime timeFrom;
    @DateTimeFormat(pattern = "hh:mm")
    LocalTime timeTo;

    @Enumerated(value = EnumType.STRING)
    DayOfWeek dayOfWeek;
}
