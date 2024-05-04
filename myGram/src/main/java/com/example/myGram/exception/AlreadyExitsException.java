package com.example.myGram.exception;

public class AlreadyExitsException extends RuntimeException{
    public AlreadyExitsException(String message) {
        super(message);
    }
}
