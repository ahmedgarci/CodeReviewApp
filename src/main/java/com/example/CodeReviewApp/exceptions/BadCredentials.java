package com.example.CodeReviewApp.exceptions;

public class BadCredentials  extends RuntimeException{

    public BadCredentials(String message) {
        super(message);
    }
    
}
