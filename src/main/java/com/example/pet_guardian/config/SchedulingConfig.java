package com.example.pet_guardian.config;

import com.example.pet_guardian.service.VaccinationScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulingConfig {

    private VaccinationScheduleService vaccinationScheduleService;

    @Scheduled(cron = "0 30 08 * * ?") // Every day at 8.30 AM
    public void scheduleVaccinationNotifications() {
        vaccinationScheduleService.notifyUpcomingVaccinations();
    }
}
