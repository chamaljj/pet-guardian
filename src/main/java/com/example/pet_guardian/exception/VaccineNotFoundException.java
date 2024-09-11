package com.example.pet_guardian.exception;

public class VaccineNotFoundException extends RuntimeException {
    public VaccineNotFoundException(String message){
        super(message);
    }

}
