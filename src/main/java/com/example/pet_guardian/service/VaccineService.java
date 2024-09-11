package com.example.pet_guardian.service;

import com.example.pet_guardian.dto.VaccineDTO;
import com.example.pet_guardian.exception.VaccineExistException;
import com.example.pet_guardian.exception.VaccineNotFoundException;
import com.example.pet_guardian.model.Vaccine;
import com.example.pet_guardian.repo.VaccineRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VaccineService {

    private VaccineRepo vaccineRepo;

    private ModelMapper modelMapper;


    // Get All Vaccines
    public List<VaccineDTO> getAllVaccines() {
        return vaccineRepo.findAll().stream()
                .map(schedule -> modelMapper.map(schedule, VaccineDTO.class))
                .toList();
    }

    // Save a new Vaccine
    public VaccineDTO saveVaccine(VaccineDTO vaccineDTO){
        // Check if pet with the same name exists
        Optional<Vaccine> existingVaccine = vaccineRepo.findByName(vaccineDTO.getName());

        if (existingVaccine.isPresent()) {
            throw new VaccineExistException("A Vaccine with the same name already exists.");
        }

        Vaccine vaccine = modelMapper.map(vaccineDTO, Vaccine.class);
        Vaccine savedVaccine = vaccineRepo.save(vaccine);
        return modelMapper.map(savedVaccine, VaccineDTO.class);
    }

    // Update Vaccine Details
    public VaccineDTO updateVaccine(Integer id, VaccineDTO vaccineDTO) {
        // Fetch the vaccine by id, throw an exception if not found
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new VaccineNotFoundException("Vaccine not found"));

        // Check if the new name and description are the same as the existing ones
        if (vaccine.getName().equals(vaccineDTO.getName())) {
            vaccine.setDescription(vaccineDTO.getDescription());
        } else {
            // Check if another vaccine exists with the new name
        Optional<Vaccine> existingVaccine = vaccineRepo.findByName(vaccineDTO.getName());
        if (existingVaccine.isPresent()) {
                // Vaccine with the same name already exists, throw an exception or handle the case
                throw new VaccineExistException("A vaccine with the same name already exists.");
            }
            else {
                // Update all fields since name and description are changing and no conflict exists
                vaccine.setName(vaccineDTO.getName());
                vaccine.setDescription(vaccineDTO.getDescription());
            }
        }
        // Save the updated vaccine entity
        Vaccine updatedVaccine = vaccineRepo.save(vaccine);

        // Map and return the updated vaccine as a DTO
        return modelMapper.map(updatedVaccine, VaccineDTO.class);
    }

    // Delete Vaccine details
    public String deleteVaccine(Integer id) {
        Vaccine vaccine = vaccineRepo.findById(id)
                .orElseThrow(() -> new VaccineNotFoundException("Vaccine not found"));

        vaccineRepo.delete(vaccine);
        return "Vaccine : " + vaccine.getName() + " has been deleted.";
    }
}
