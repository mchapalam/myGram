package com.example.myGram.exception;

import java.text.MessageFormat;

public class RefreshTokenException extends RuntimeException{
    public RefreshTokenException(String token, String message) {
        super(MessageFormat.format("Error token {}" , token));
    }

    public RefreshTokenException(String message) {
        super(message);
    }
}
