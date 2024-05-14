package com.aman.socialMedia.Exceptions;


import com.aman.socialMedia.Models.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> resourceNotFoundException(ResourceNotFoundException ce) {
        return new ResponseEntity<>(new ResponseDTO(null, ce.getMessage(), true), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAccessException.class)
    public ResponseEntity<ResponseDTO> invalidAccessException(InvalidAccessException ce){
        return new ResponseEntity<>(new ResponseDTO(null , ce.getMessage() , true), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String , String>>  methodArgumentNotValidException (MethodArgumentNotValidException ce){
         Map<String , String> errorMap = new HashMap<>();
         ce.getBindingResult().getAllErrors().forEach((error)->{
             String fieldName = ((FieldError) error).getField();
             String message = error.getDefaultMessage();
             errorMap.put(fieldName , message);
         });

         return new ResponseEntity<>(errorMap , HttpStatus.BAD_REQUEST);

    }


}
