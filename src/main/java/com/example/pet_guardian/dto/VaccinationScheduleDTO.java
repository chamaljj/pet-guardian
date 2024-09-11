package com.example.pet_guardian.dto;

import com.example.pet_guardian.model.Pet;
import com.example.pet_guardian.model.Vaccine;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationScheduleDTO {

    private int id;
    private Pet pet;
    private Vaccine vaccine;
    private LocalDate vaccinationDate;

}
