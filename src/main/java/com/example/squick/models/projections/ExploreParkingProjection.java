package com.example.squick.models.projections;

public interface ExploreParkingProjection {

    Long getId();

    Integer getHourlyPrice();

    Integer getNumberOfFreeSpaces();

    String getName();

    String getLocationAddress();

    String getImage();

    Double getLongitude();

    Double getLatitude();
}
