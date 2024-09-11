package com.example.pet_guardian.exception;

public class PetNotFoundException extends RuntimeException{

    public PetNotFoundException(String message){
        super(message);
    }
}
