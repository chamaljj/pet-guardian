package com.example.pet_guardian.controller;

import com.example.pet_guardian.dto.PetDTO;
import com.example.pet_guardian.service.PetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "api/v1")
public class PetController {

    private PetService petService;

    // View All Pets Details
    @GetMapping("/getallpets")
    public List<PetDTO> getPets(){
            return petService.getAllPets();
    }

    // Add new Pet Details
    @PostMapping("/addpet")
    public ResponseEntity<?> savePet(@RequestBody PetDTO petDTO) {
        try {
            PetDTO savedPet = petService.savePet(petDTO);
            log.info("Save a New Pet Details");
            log.info("New pet details: " +savedPet);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
        } catch (RuntimeException e) {
            log.error("Not Save Error Reason : " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Update an existing Pet Details
    @PutMapping("/updatepet/{id}")
    public ResponseEntity<?> updatePet(@PathVariable Integer id, @RequestBody PetDTO petDTO) {
        try {
            PetDTO updatedPet = petService.updatePet(id, petDTO);
            log.info("Pet of Id Details Updated: "+id);
            log.info("Updated Pet Details" +updatedPet);
            return ResponseEntity.status(HttpStatus.OK).body(updatedPet);
        }
        catch (RuntimeException e) {
            log.error("Not Updated this pet details:" +id);
            log.error("Not Update Reason : " +e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    //  Delete Pet Details
    @DeleteMapping("deletepet/{id}")
    public ResponseEntity<String> deletePet(@PathVariable Integer id) {
        try {
            String result = petService.deletePet(id);
            log.info("Successfully delete pet id: "+id);
            return ResponseEntity.ok(result);
        }
        catch(RuntimeException e) {
            log.error("Not Delete this Pet id: "+id+ " Error Reason: " +e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
