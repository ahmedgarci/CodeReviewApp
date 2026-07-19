package com.example.CodeReviewApp.exceptions;

public class ActionNotAllowedException extends RuntimeException {

    public ActionNotAllowedException(String message) {
        super(message);
    }
    
}
