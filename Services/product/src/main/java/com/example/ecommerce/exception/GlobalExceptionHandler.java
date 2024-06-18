package com.example.ecommerce.exception;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
//import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<String> handleProductPurchaseException(ProductPurchaseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

       var errors=new HashMap<String,String>();
        e.getBindingResult().getAllErrors().forEach(objectError ->
        {
            var FieldName=((FieldError)objectError).getField();
            var errorMsg=objectError.getDefaultMessage();
            errors.put(FieldName,errorMsg);

        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));


    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handle(EntityNotFoundException exp){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exp.getMessage());
    }

}
