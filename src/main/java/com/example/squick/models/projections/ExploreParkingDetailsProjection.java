package com.example.squick.models.projections;

import com.example.squick.models.WorkingHours;

import java.util.List;

public interface ExploreParkingDetailsProjection {

    String getName();

    String getLocationAddress();

    Double getLongitude();

    Double getLatitude();

    Integer getHourlyPrice();

    Integer getMonthlyPrice();

    Integer getYearlyPrice();

    Integer getCapacity();

    Integer getNumberOfFreeSpaces();

    String getImageUrl();

    List<WorkingHours> getWorkingHours();
}
