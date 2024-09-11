package com.example.pet_guardian.controller;

import com.example.pet_guardian.dto.PetDTO;
import com.example.pet_guardian.dto.VaccinationScheduleDTO;
import com.example.pet_guardian.dto.VaccineDTO;
import com.example.pet_guardian.service.VaccineService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "api/v1")
@Slf4j
public class VaccineController {

    private VaccineService vaccineService;

    // Get All vaccines
    @GetMapping("/getallvaccines")
    public List<VaccineDTO> getAllVaccines(){
            return vaccineService.getAllVaccines();
    }

    // Add new Vaccine Details
    @PostMapping("/addvaccine")
    public ResponseEntity<?> saveVaccine(@RequestBody VaccineDTO vaccineDTO) {
        try {
            VaccineDTO savedVaccine = vaccineService.saveVaccine(vaccineDTO);
            log.info("Successfully Save a New Vaccine Details");
            log.info("New Schedule details: " +savedVaccine);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedVaccine);
        } catch (RuntimeException e) {
            log.error("Not Save Vaccine Error Reason : " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update an existing vaccine Details
    @PutMapping("/updatevaccine/{id}")
    public ResponseEntity<?> updateVaccine(@PathVariable Integer id, @RequestBody VaccineDTO vaccineDTO) {
       try{
            VaccineDTO updatedVaccine = vaccineService.updateVaccine(id, vaccineDTO);
            log.info("Vaccine of Id Details Updated: "+id);
            log.info("Updated Vaccine Details" +updatedVaccine);
            return ResponseEntity.ok(updatedVaccine);
        }
        catch (RuntimeException e) {
            log.error("Not Updated this Vaccine details:" +id);
            log.error("Not Update Reason : " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Delete Vaccine Details
    @DeleteMapping("deletevaccine/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Integer id) {
        try {
            String result = vaccineService.deleteVaccine(id);
            log.info("Successfully delete vaccine id: "+id);
            return ResponseEntity.ok(result);
        }
        catch (RuntimeException e) {
            log.error("Not Delete this Vaccine id: "+id+ " Error Reason: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
