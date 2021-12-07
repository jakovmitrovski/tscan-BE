package com.example.squick.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "parkings")
public class Parking {

    public Parking() {
    }

    public Parking(String name, String locationAddress, Double longitude, Double latitude, Integer hourlyPrice, Integer monthlyPrice, Integer yearlyPrice, Integer capacity, Integer numberOfFreeSpaces, String imageUrl, Set<WorkingHours> workingHours) {
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
    Set<WorkingHours> workingHours;
}
