package com.example.squick.repositories;

import com.example.squick.models.Parking;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query(value = "select p.id, p.capacity, p.hourly_price, p.image_url_small, p.image_url_medium, p.image_url_large, p.latitude, p.location_address, p.longitude, p.monthly_price, p.name, p.number_of_free_spaces, p.yearly_price  from (parkings p join parkings_working_hours pw on p.id = pw.parking_id join working_hours w on pw.working_hours_id = w.id) where p.number_of_free_spaces >= CASE WHEN :freeSpaces = true THEN 1 ELSE 0 END and w.day_of_week = :day and :time >= w.time_from and :time <= w.time_to and  p.hourly_price >= :priceselect and p.hourly_price <= :priceTo and (p.name like :keyword or p.location_address like :keyword)", nativeQuery = true)
    List<Parking> findAllExploreOpenNow(@Param("priceselect") Integer priceFrom,
                                        @Param("priceTo") Integer priceTo,
                                        @Param("freeSpaces") boolean freeSpaces,
                                        @Param("keyword") String keyword,
                                        @Param("day") String day,
                                        @Param("time") LocalTime time);

    @Query(value = "select * from parkings where number_of_free_spaces >= CASE WHEN :freeSpaces = true THEN 1 ELSE 0 END and hourly_price >= :priceFrom and hourly_price <= :priceTo and (name like :keyword or location_address like :keyword)", nativeQuery = true)
    List<Parking> findAllExplore(Integer priceFrom, Integer priceTo, boolean freeSpaces, String keyword);

    Optional<ExploreParkingDetailsProjection> findParkingById(Long id);

    Optional<Parking> findByName(String name);
}
