package com.example.pet_guardian.controller;

import com.example.pet_guardian.dto.PetDTO;
import com.example.pet_guardian.dto.VaccinationScheduleDTO;
import com.example.pet_guardian.model.VaccinationSchedule;
import com.example.pet_guardian.service.VaccinationScheduleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1")
@Slf4j
public class VaccinationScheduleController {


    private VaccinationScheduleService vaccinationScheduleService;

    // View All Vaccination Schedules
    @GetMapping("/getallschedules")
    public List<VaccinationScheduleDTO> getScheduless(){
        return vaccinationScheduleService.getAllVaccinationSchedules();
    }

    // Add new Vaccination Schedule
    @PostMapping("/addschedule")
    public ResponseEntity<?> addSchedule(@RequestBody VaccinationScheduleDTO vaccinationScheduleDTO) {
        try {
            VaccinationScheduleDTO createdSchedule = vaccinationScheduleService.addVaccinationSchedule(vaccinationScheduleDTO);
            log.info("Save a New Schedule Details");
            log.info("New Schedule details: " +createdSchedule);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
        }
        catch (RuntimeException e) {
            log.error("Not Save Schedule Error Reason : " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update an existing vaccination schedule
    @PutMapping("/updateschedule/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Integer id, @RequestBody VaccinationScheduleDTO vaccinationScheduleDTO) {
        try {
            VaccinationScheduleDTO updatedSchedule = vaccinationScheduleService.updateVaccinationSchedule(id, vaccinationScheduleDTO);
            log.info("Schedule of Id Details Updated: "+id);
            log.info("Updated Schedule Details" +updatedSchedule);
            return ResponseEntity.ok(updatedSchedule);
        }
        catch (RuntimeException e) {
            log.error("Not Updated this Schedule details:" +id);
            log.error("Not Update Reason : " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete a vaccination schedule
    @DeleteMapping("deleteschedule/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Integer id) {
        try {
            String result = vaccinationScheduleService.deleteVaccinationSchedule(id);
            log.info("Successfully delete Schedule id: "+id);
            return ResponseEntity.ok(result);
        }
        catch (RuntimeException e) {
            log.error("Not Delete this Schedule id: "+id+ " Error Reason: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}


