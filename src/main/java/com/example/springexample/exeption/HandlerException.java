package com.example.springexample.exeption;


import com.example.springexample.dto.ExceptionDto;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(CastomException.class)
    public ResponseEntity<ExceptionDto> notFoundException(CastomException e, HttpServletRequest request){
        ExceptionDto error = new ExceptionDto(HttpStatus.NOT_FOUND.value(), "Not found", e.getMessage(), LocalDateTime.now(),request.getRequestURI());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> badRequestException(BadRequestException e,HttpServletRequest request){
        ExceptionDto error = new ExceptionDto(HttpStatus.BAD_REQUEST.value(),"Bad request", e.getMessage(), LocalDateTime.now(),request.getRequestURI());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> validationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> allException(Exception e, HttpServletRequest request){
        ExceptionDto error = new ExceptionDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Server error", "unexpected error",LocalDateTime.now(),request.getRequestURI());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
