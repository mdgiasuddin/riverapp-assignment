package org.example.river.exception;

import org.example.river.model.dto.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResponse handleException(ResourceNotFoundException e) {
        return new ExceptionResponse("", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RulesViolationException.class)
    public ExceptionResponse handleException(RulesViolationException e) {
        return new ExceptionResponse("", e.getMessage());
    }

    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    @ExceptionHandler(SQLException.class)
    public ExceptionResponse handleException(SQLException e) {
        return new ExceptionResponse("SQL_VALIDATION_FAILED", e.getMessage());
    }


    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(String.format("%s: %s", fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return new ExceptionResponse(
                "VALIDATION_FAILED",
                String.join(". ", errors)
        );
    }
}
