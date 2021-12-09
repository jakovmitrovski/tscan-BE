package com.example.squick.models;

import com.example.squick.models.dtos.ParkingDto;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "parkings")
public class Parking {

    public Parking() {
    }

    public Parking(String name, String locationAddress, Double longitude, Double latitude, Integer hourlyPrice, Integer monthlyPrice, Integer yearlyPrice, Integer capacity, Integer numberOfFreeSpaces, String imageUrl, List<WorkingHours> workingHours) {
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
        this.workingHours = workingHours;
    }

    public Parking(ParkingDto parkingDto, List<WorkingHours> workingHours) {
        this.name = parkingDto.getName();
        this.locationAddress = parkingDto.getLocationAddress();
        this.longitude = parkingDto.getLongitude();
        this.latitude = parkingDto.getLatitude();
        this.hourlyPrice = parkingDto.getHourlyPrice();
        this.monthlyPrice = parkingDto.getMonthlyPrice();
        this.yearlyPrice = parkingDto.getYearlyPrice();
        this.capacity = parkingDto.getCapacity();
        this.numberOfFreeSpaces = parkingDto.getNumberOfFreeSpaces();
        this.imageUrl = parkingDto.getImageUrl();
        this.workingHours = workingHours;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

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

    @ManyToMany
    List<WorkingHours> workingHours;
}
