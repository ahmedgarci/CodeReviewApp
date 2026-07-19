package com.example.CodeReviewApp.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestAdvice {

    @ExceptionHandler(RessourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRessourceNotFound(RessourceNotFoundException exception){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity(new ErrorResponse(status.value(), exception.getMessage()),status);
    }
    

    @ExceptionHandler(RessourceAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleRessourceNotFound(RessourceAlreadyExists exception){
        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity(new ErrorResponse(status.value(), exception.getMessage()),status);
    }

    @ExceptionHandler(BadCredentials.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentials exception){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity(new ErrorResponse(status.value(), exception.getMessage()),status);
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handleNotAllowedActions(ActionNotAllowedException exception){
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity(new ErrorResponse(status.value(), exception.getMessage()),status);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException exception){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity(new ErrorResponse(status.value(), exception.getMessage()),status);
    }

    @ExceptionHandler(CustomJwtExcpetion.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(CustomJwtExcpetion exception){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return new ResponseEntity(new ErrorResponse(status.value(), exception.getMessage()),status);
    }

    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleInvalidFormInputs(MethodArgumentNotValidException ex){
        List<FieldError> fieldErrors = ex.getFieldErrors();
        Map<String,String> map = new HashMap<>();
        fieldErrors.forEach(error -> {
            String fieldName = error.getField();
            String flattenedFieldName = fieldName.substring(fieldName.indexOf(".")+1);
            map.put(flattenedFieldName, error.getDefaultMessage());
        });
       return ResponseEntity.badRequest().body(map);
    }
}
