package com.example.squick.models.projections;

public interface MapParkingProjection {

    Long getId();

    Integer getHourlyPrice();

    Integer getNumberOfFreeSpaces();

    String getLocationAddress();

    String getName();

    Double getLongitude();

    Double getLatitude();
}
