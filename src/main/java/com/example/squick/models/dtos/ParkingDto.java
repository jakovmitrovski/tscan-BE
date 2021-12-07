package com.example.squick.models.dtos;

import com.example.squick.models.WorkingHours;
import lombok.Data;

import java.util.List;

@Data
public class ParkingDto {

    String name;

    String locationAddress;

    Double longitude;

    Double latitude;

    Integer hourlyPrice;

    Integer monthlyPrice;

    Integer yearlyPrice;

    Integer capacity;

    Integer numberOfFreeSpaces;

    String imageUrl;

    List<Long> workingHoursIds;

    public ParkingDto(String name, String locationAddress, Double longitude, Double latitude, Integer hourlyPrice, Integer monthlyPrice, Integer yearlyPrice, Integer capacity, Integer numberOfFreeSpaces, String imageUrl, List<Long> workingHours) {
        this.name = name;
        this.locationAddress = locationAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.hourlyPrice = hourlyPrice;
        this.monthlyPrice = monthlyPrice;
        this.yearlyPrice = yearlyPrice;
        this.capacity = capacity;
        this.numberOfFreeSpaces = numberOfFreeSpaces;
        this.imageUrl = imageUrl;
        this.workingHoursIds = workingHours;
    }
}
