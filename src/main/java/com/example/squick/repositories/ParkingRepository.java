package com.example.squick.repositories;

import com.example.squick.models.Parking;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

    @Query(value = "select p.id, p.capacity, p.hourly_price, p.image_url, p.latitude, p.location_address, p.longitude, p.monthly_price, p.name, p.number_of_free_spaces, p.yearly_price  from (parkings p join parkings_working_hours pw on p.id = pw.parking_id join working_hours w on pw.working_hours_id = w.id) where w.day_of_week = :day and :time >= w.time_from and :time <= w.time_to and  p.hourly_price >= :priceselect and p.hourly_price <= :priceTo and (p.name like :keyword or p.location_address like :keyword)", nativeQuery = true)
    Page<Parking> findAllExploreOpenNow(@Param("priceselect") Integer priceFrom,
                                        @Param("priceTo") Integer priceTo,
                                        @Param("keyword")String keyword,
                                        @Param("day")String day,
                                        @Param("time")LocalTime time, Pageable pageable);
    @Query(value = "select * from parkings where hourly_price >= :priceFrom and hourly_price <= :priceTo and (name like :keyword or location_address like :keyword)", nativeQuery = true)
    Page<Parking> findAllExplore(Integer priceFrom, Integer priceTo, String keyword,Pageable pageable);
    @Query(value = "select * from parkings", nativeQuery = true)
    List<Parking> findAllMap();
    Optional<ExploreParkingDetailsProjection> findParkingById(Long id);
    Optional<Parking> findByName(String name);
}
