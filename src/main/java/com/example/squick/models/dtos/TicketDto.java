package com.example.squick.models.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class TicketDto {

    @NotNull(message = "Паркингот е задолжителен!")
    Long parkingId;

    @NotNull(message = "Вредноста е задолжителна!")
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
