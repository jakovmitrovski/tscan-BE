package com.example.squick.models.dtos;

import com.example.squick.models.Parking;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class TicketResponseDto {
    Long id;
    Parking parking;

    String entered;

    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    String exited;

    Long price;

    public TicketResponseDto(Long id, Parking parking, String entered, String exited) {
        this.id = id;
        this.parking = parking;
        this.entered = entered;
        this.exited = exited;
    }
}
