package com.ecommerce.user_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> handleExsist(ResourceAlreadyExistsException ex){
        Map<String,Object> m=Map.of("Error","Conflict","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(m);

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleNotFound(ResourceNotFoundException e){
        Map<String ,Object> map=Map.of("Error","NotFound","message",e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidation(MethodArgumentNotValidException mex){
        Map<String,Object> error=new HashMap<>();
        mex.getBindingResult().getFieldErrors().forEach(err->error.put(err.getField(),err.getDefaultMessage()));
        Map<String,Object> body=Map.of("error","Validation Failed","details",error);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleAllException(Exception ex){
        Map<String,Object> m=Map.of("Error","InternalServerError","message",ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(m);

    }
}
