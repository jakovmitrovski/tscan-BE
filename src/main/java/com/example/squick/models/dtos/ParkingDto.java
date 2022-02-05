package com.example.squick.models.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ParkingDto {

    @NotNull(message = "Името е задолжително!")
    String name;

    @NotNull(message = "Адресата е задолжителна!")
    String locationAddress;

    @NotNull(message = "Географската ширина е задолжителна!")
    Double longitude;

    @NotNull(message = "Географската должина е задолжителна!")
    Double latitude;

    @NotNull(message = "Часовната цена е задолжителна!")
    Integer hourlyPrice;

    @NotNull(message = "Месечната цена е задолжителна!")
    Integer monthlyPrice;

    @NotNull(message = "Годишната цена е задолжителна!")
    Integer yearlyPrice;

    @NotNull(message = "Капацитетот на паркингот е задолжителен!")
    Integer capacity;

    @NotNull(message = "Бројот на слободни места е задолжителен!")
    Integer numberOfFreeSpaces;

    String imageUrlSmall;

    String imageUrlMedium;

    String imageUrlLarge;

    @NotEmpty(message = "Работните часови се задолжителни!")
    List<Long> workingHoursIds;

    public ParkingDto(String name, String locationAddress, Double longitude, Double latitude, Integer hourlyPrice, Integer monthlyPrice, Integer yearlyPrice, Integer capacity, Integer numberOfFreeSpaces, String imageUrlSmall, String imageUrlMedium, String imageUrlLarge, List<Long> workingHours) {
        this.name = name;
        this.locationAddress = locationAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hourlyPrice = hourlyPrice;
        this.monthlyPrice = monthlyPrice;
        this.yearlyPrice = yearlyPrice;
        this.capacity = capacity;
        this.numberOfFreeSpaces = numberOfFreeSpaces;
        this.imageUrlSmall = imageUrlSmall;
        this.imageUrlMedium = imageUrlMedium;
        this.imageUrlLarge = imageUrlLarge;
        this.workingHoursIds = workingHours;
    }
}
