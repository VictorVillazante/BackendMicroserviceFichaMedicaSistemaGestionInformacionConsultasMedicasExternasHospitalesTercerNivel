package com.example.microserviciofichasmedicas.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.microserviciofichasmedicas.util.exceptions.BusinessValidationException;

@ControllerAdvice 
public class GlobalExceptionHandler { 
    @ExceptionHandler(BusinessValidationException.class) 
    public ResponseEntity<String> handleBusinessValidationException(BusinessValidationException ex) { 
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getErrorMessage(), HttpStatus.BAD_REQUEST); 
    } 
    @ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleException(Exception ex) { 
        ex.printStackTrace();
        return new ResponseEntity<>("Ha ocurrido un error inesperado en el servidor.", HttpStatus.INTERNAL_SERVER_ERROR); 
    } 
}
