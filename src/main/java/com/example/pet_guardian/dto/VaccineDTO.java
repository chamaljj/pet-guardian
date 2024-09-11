package com.example.pet_guardian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {

    private int id;
    private String name;
    private String description;

}
