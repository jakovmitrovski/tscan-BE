package com.example.squick.apis;

import com.example.squick.models.WorkingHours;
import com.example.squick.models.dtos.WorkingHoursDto;
import com.example.squick.models.exceptions.BadRequestException;
import com.example.squick.models.responses.ResponseMessage;
import com.example.squick.services.WorkingHoursService;
import com.example.squick.utils.Constants;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/working-hours")
public class WorkingHoursController {

    private final WorkingHoursService workingHoursService;

    public WorkingHoursController(WorkingHoursService workingHoursService) {
        this.workingHoursService = workingHoursService;
    }

    @GetMapping("/{parkingId}")
    List<WorkingHours> getAllWorkingHoursForParking(@PathVariable Long parkingId) {
        return workingHoursService.findAllWorkingHoursForParking(parkingId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    ResponseEntity<WorkingHours> addNewWorkingHoursForParking(@RequestBody WorkingHoursDto dto) {
        return workingHoursService.createWorkingHoursForParking(dto).map(success -> ResponseEntity.ok(success))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<ResponseMessage> editWorkingHoursForParking(@PathVariable Long id,
                                                               @RequestBody WorkingHoursDto dto) {

        ResponseMessage message = new ResponseMessage(Constants.editSuccessful);

        return workingHoursService.edit(dto, id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseMessage> deleteWorkingHours(@PathVariable Long id) {

        ResponseMessage message = new ResponseMessage(Constants.workingHoursDeletedSuccessfully);

        return workingHoursService.delete(id).map(success -> ResponseEntity.ok(message))
                .orElseThrow(() -> new BadRequestException(Constants.badRequest));
    }
}
