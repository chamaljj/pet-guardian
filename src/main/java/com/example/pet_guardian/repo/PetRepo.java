package com.example.pet_guardian.repo;

import com.example.pet_guardian.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PetRepo extends JpaRepository<Pet,Integer> {

    Optional<Pet> findByNameAndOwnerEmail(String name, String ownerEmail);

}
