package com.example.squick.models.dtos;

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

    @DateTimeFormat(pattern = "dd.mm.yyyy HH:MM:SS")
    LocalDateTime entered;

    @DateTimeFormat(pattern = "dd.mm.yyyy HH:MM:SS")
    LocalDateTime exited;

    public TicketDto(Long parkingId, Long value, LocalDateTime entered, LocalDateTime exited) {
        this.parkingId = parkingId;
        this.value = value;
        this.entered = entered;
        this.exited = exited;
    }
}
