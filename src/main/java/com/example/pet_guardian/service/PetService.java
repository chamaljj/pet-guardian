package com.example.pet_guardian.service;

import com.example.pet_guardian.dto.PetDTO;
import com.example.pet_guardian.exception.PetAndOwnerEmailExistException;
import com.example.pet_guardian.exception.PetNotFoundException;
import com.example.pet_guardian.model.Pet;
import com.example.pet_guardian.repo.PetRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class PetService {

    private PetRepo petRepo;

    private ModelMapper modelMapper;


    // Get All Pets Details
    public List<PetDTO> getAllPets(){
        return petRepo.findAll().stream()
                .map(schedule -> modelMapper.map(schedule, PetDTO.class))
                .toList();
    }

    // Post a new Pet Details
    public PetDTO savePet(PetDTO petDTO) {
        // Check if pet with the same name and owner email exists
        Optional<Pet> existingPet = petRepo.findByNameAndOwnerEmail(petDTO.getName(), petDTO.getOwnerEmail());

        if (existingPet.isPresent()) {
            throw new PetAndOwnerEmailExistException("A pet with the same name and owner email already exists.");
        }

        Pet pet = modelMapper.map(petDTO, Pet.class);
        Pet savedPet = petRepo.save(pet);
        return modelMapper.map(savedPet, PetDTO.class);
    }

    public PetDTO updatePet(Integer id, PetDTO petDTO) {
        // Fetch the pet by id, throw an exception if not found
        Pet pet = petRepo.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found"));

        // Check if the new pet name and email are the same as the existing ones
        if (pet.getName().equals(petDTO.getName()) && pet.getOwnerEmail().equals(petDTO.getOwnerEmail())) {
            // Same name and email, only update weight and ownerName
            pet.setWeight(petDTO.getWeight());
            pet.setOwnerName(petDTO.getOwnerName());
        } else {
            // Check if another pet exists with the new name and email
            Optional<Pet> existingPet = petRepo.findByNameAndOwnerEmail(petDTO.getName(), petDTO.getOwnerEmail());
            if (existingPet.isPresent()) {
                // Pet with the same pet name and email already exists, throw an exception or handle the case
                throw new PetNotFoundException("A pet with the same name and email already exists.");
            } else {
                // Update all fields since pet name and email are changing and no conflict exists
                pet.setName(petDTO.getName());
                pet.setWeight(petDTO.getWeight());
                pet.setOwnerName(petDTO.getOwnerName());
                pet.setOwnerEmail(petDTO.getOwnerEmail());
            }
        }
        // Save the updated pet entity
        Pet updatedPet = petRepo.save(pet);

        // Map and return the updated pet as a DTO
        return modelMapper.map(updatedPet, PetDTO.class);
    }

    // Delete a pet details
    public String deletePet(Integer id) {
        Pet pet = petRepo.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found"));
        petRepo.delete(pet);
        return pet.getOwnerName() + "'s " + pet.getName() + " has been deleted.";
    }
}
