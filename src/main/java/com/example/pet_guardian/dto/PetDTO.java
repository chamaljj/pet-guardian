package com.example.pet_guardian.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PetDTO {

    private int id;
    private String name;
    private BigDecimal weight;
    private String ownerName;
    private String ownerEmail;

}
