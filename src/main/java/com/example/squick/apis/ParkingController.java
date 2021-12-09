package com.example.squick.apis;

import com.example.squick.models.Parking;
import com.example.squick.models.dtos.ParkingDto;
import com.example.squick.models.exceptions.CustomNotFoundException;
import com.example.squick.models.projections.ExploreParkingDetailsProjection;
import com.example.squick.models.responses.ResponseMessage;
import com.example.squick.services.ParkingService;
import com.example.squick.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/parkings")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/explore")
    public Page<Parking> findAllExplore(@RequestParam(required = false, defaultValue = "0") Integer start,
                                        @RequestParam(required = false, defaultValue = "10") Integer items,
                                        @RequestParam(required = false, defaultValue = "0") Integer priceFrom,
                                        @RequestParam(required = false, defaultValue = "1000000") Integer priceTo,
                                        @RequestParam(required = false, defaultValue = "false") boolean openNow,
                                        @RequestParam(required = false, defaultValue = "%") String keyword) {
        return this.parkingService.findAllExplore(start, items, priceFrom, priceTo, openNow, keyword);
    }

    @GetMapping("/explore/{id}")
    public ResponseEntity<ExploreParkingDetailsProjection> findParkingByIdExplore(@PathVariable Long id) {
        return this.parkingService.findByIdExplore(id).map(parking -> ResponseEntity.ok().body(parking))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }

    @GetMapping("/map")
    public List<Parking> findAllMap() {
        return this.parkingService.findAllMap();
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> createParking(@Valid @RequestBody ParkingDto parkingDto) {
        ResponseMessage message = new ResponseMessage(Constants.parkingCreatedSuccessfully);
        return this.parkingService.create(parkingDto).map(success -> ResponseEntity.ok().body(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> editParking(@Valid @RequestBody ParkingDto parkingDto, @PathVariable Long id) {
        ResponseMessage message = new ResponseMessage(Constants.editSuccessful);

        return this.parkingService.edit(parkingDto, id).map(success -> ResponseEntity.ok().body(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteParking(@PathVariable Long id) {
        ResponseMessage message = new ResponseMessage(Constants.parkingDeletedSuccessfully);

        return this.parkingService.delete(id).map(success -> ResponseEntity.ok().body(message))
                .orElseThrow(() -> new CustomNotFoundException(Constants.parkingNotFoundMessage));
    }
}
