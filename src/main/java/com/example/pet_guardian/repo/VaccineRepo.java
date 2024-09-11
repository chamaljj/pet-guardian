package com.example.pet_guardian.repo;

import com.example.pet_guardian.model.Pet;
import com.example.pet_guardian.model.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccineRepo extends JpaRepository<Vaccine, Integer> {
    Optional<Vaccine> findByName(String name);

    Optional<Vaccine> findByDescription(String description);

    Optional<Vaccine> findByNameAndDescription(String name, String description);
}
