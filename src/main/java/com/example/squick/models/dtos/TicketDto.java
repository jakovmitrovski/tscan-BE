package com.example.squick.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class TicketDto {
    Long parkingId;

    Long value;

    String entered;

    String exited;

    public TicketDto(Long parkingId, Long value, String entered, String exited) {
        this.parkingId = parkingId;
        this.value = value;
        this.entered = entered;
        this.exited = exited;
    }
}
