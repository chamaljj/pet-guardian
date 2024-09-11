package com.example.pet_guardian.repo;

import com.example.pet_guardian.model.VaccinationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccinationScheduleRepo extends JpaRepository<VaccinationSchedule, Integer> {
    List<VaccinationSchedule> findByVaccinationDateBetween(LocalDate startDate, LocalDate endDate);


}
