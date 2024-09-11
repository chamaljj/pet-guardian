package com.example.pet_guardian.exception;

public class PetAndOwnerEmailExistException extends RuntimeException{

    public PetAndOwnerEmailExistException(String message){
        super(message);
    }

}
