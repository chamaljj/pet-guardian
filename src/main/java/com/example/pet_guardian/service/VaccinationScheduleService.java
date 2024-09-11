package com.example.pet_guardian.service;

import com.example.pet_guardian.dto.VaccinationScheduleDTO;
import com.example.pet_guardian.model.Pet;
import com.example.pet_guardian.model.VaccinationSchedule;
import com.example.pet_guardian.model.Vaccine;
import com.example.pet_guardian.repo.PetRepo;
import com.example.pet_guardian.repo.VaccinationScheduleRepo;
import com.example.pet_guardian.repo.VaccineRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class VaccinationScheduleService {

    private VaccinationScheduleRepo vaccinationScheduleRepo;

    private EmailService emailService;

    private PetRepo petRepo;

    private VaccineRepo vaccineRepo;

    private ModelMapper modelMapper;

    // Get All Vaccination schedules
    public List<VaccinationScheduleDTO> getAllVaccinationSchedules() {
        return vaccinationScheduleRepo.findAll().stream()
            .map(schedule -> modelMapper.map(schedule, VaccinationScheduleDTO.class))
            .toList();
    }

    //    Upcoming Vaccinations notifications
    public void notifyUpcomingVaccinations() {
        LocalDate today = LocalDate.now();
        LocalDate upcoming = today.plusDays(2); // Notify Two day before

        List<VaccinationSchedule> upcomingVaccinations =
                vaccinationScheduleRepo.findByVaccinationDateBetween(today, upcoming);

        for (VaccinationSchedule schedule : upcomingVaccinations) {
            Pet pet = schedule.getPet();
            Vaccine vaccine = schedule.getVaccine();

            String subject = "Upcoming Your Pet Vaccination Reminder";
            String message = String.format("""
                            Dear %s,

                            Your pet %s is scheduled for a %s vaccination on %s.
                            Please ensure to bring your pet on 9.00 A.M.

                            Thank You.
                            Best Regards,
                            Pet Clinic Home.""",
                    pet.getOwnerName(), pet.getName(), vaccine.getName(), schedule.getVaccinationDate());


            emailService.sendEmail(pet.getOwnerEmail(), subject, message);
        }
    }

    // Add new vaccination schedule
    public VaccinationScheduleDTO addVaccinationSchedule(VaccinationScheduleDTO vaccinationScheduleDTO) {
        VaccinationSchedule vaccinationSchedule = new VaccinationSchedule();

        Pet pet = petRepo.findById(vaccinationScheduleDTO.getPet().getId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        Vaccine vaccine = vaccineRepo.findById(vaccinationScheduleDTO.getVaccine().getId())
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        vaccinationSchedule.setPet(pet);
        vaccinationSchedule.setVaccine(vaccine);
        vaccinationSchedule.setVaccinationDate(vaccinationScheduleDTO.getVaccinationDate());

        VaccinationSchedule savedSchedule = vaccinationScheduleRepo.save(vaccinationSchedule);
        return modelMapper.map(savedSchedule, VaccinationScheduleDTO.class);
    }

    // Update vaccination schedule
    public VaccinationScheduleDTO updateVaccinationSchedule(Integer id, VaccinationScheduleDTO vaccinationScheduleDTO) {
        VaccinationSchedule vaccinationSchedule = vaccinationScheduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccination Schedule not found"));

        Pet pet = petRepo.findById(vaccinationScheduleDTO.getPet().getId())
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        Vaccine vaccine = vaccineRepo.findById(vaccinationScheduleDTO.getVaccine().getId())
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));

        vaccinationSchedule.setPet(pet);
        vaccinationSchedule.setVaccine(vaccine);
        vaccinationSchedule.setVaccinationDate(vaccinationScheduleDTO.getVaccinationDate());

        VaccinationSchedule updatedSchedule = vaccinationScheduleRepo.save(vaccinationSchedule);
        return modelMapper.map(updatedSchedule, VaccinationScheduleDTO.class);
    }

    //    Delete vaccination schedule
    public String deleteVaccinationSchedule(Integer id) {
        VaccinationSchedule vaccinationSchedule = vaccinationScheduleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccination Schedule not found"));

        vaccinationScheduleRepo.delete(vaccinationSchedule);
        return "Vaccination schedule for pet " + vaccinationSchedule.getPet().getName() + " with vaccine " +
                vaccinationSchedule.getVaccine().getName() + " on " + vaccinationSchedule.getVaccinationDate() + " has been deleted.";
    }
}
