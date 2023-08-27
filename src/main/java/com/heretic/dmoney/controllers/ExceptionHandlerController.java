package com.heretic.dmoney.controllers;

import com.heretic.dmoney.dto.responses.ErrorResponse;
import com.heretic.dmoney.errors.Error;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleEntityNotFoundException(EntityNotFoundException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.builder()
                .httpStatus(BAD_REQUEST)
                .time(now())
                .message(exception.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.builder()
                .errorCount(exception.getFieldErrorCount())
                .time(now())
                .httpStatus(BAD_REQUEST)
                .errors(List.copyOf(buildErrors(exception)))
                .build();
    }

    private List<Error> buildErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new Error(fieldError.getDefaultMessage(), fieldError.getField()))
                .toList();
    }
}