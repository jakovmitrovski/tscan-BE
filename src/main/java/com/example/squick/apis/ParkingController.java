package com.example.squick.apis;

import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import com.example.squick.models.projections.ExploreParkingProjection;
import com.example.squick.models.projections.MapParkingProjection;
import com.example.squick.services.ParkingService;
import com.example.squick.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/explore")
    public Page<ExploreParkingProjection> findAllExplore() {
        return this.parkingService.findAllExplore();
    }

    @GetMapping("/explore/{id}")
    public ResponseEntity<ExploreParkingDetailsProjection> findParkingByIdExplore(@PathVariable Long id) {
        return this.parkingService.findByIdExplore(id).map(parking -> ResponseEntity.ok().body(parking))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }

    @GetMapping("/map")
    public List<MapParkingProjection> findAllMap() {
        return this.parkingService.findAllMap();
    }
}
