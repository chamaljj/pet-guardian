package com.example.pet_guardian.exception;

public class VaccineExistException extends RuntimeException{

    public VaccineExistException(String message){
        super(message);
    }
}
