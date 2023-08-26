package com.heretic.dmoney.controllers;

import com.heretic.dmoney.dto.responses.ErrorResponse;
import com.heretic.dmoney.errors.Error;
import com.heretic.dmoney.errors.ValidationError;
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
                .errorsCount(1)
                .errors(buildErrors(exception))
                .httpStatus(BAD_REQUEST)
                .time(now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        return ErrorResponse.builder()
                .errorsCount(exception.getFieldErrorCount())
                .time(now())
                .httpStatus(BAD_REQUEST)
                .errors(List.copyOf(buildValidationErrors(exception)))
                .build();
    }

    private List<Error> buildErrors(EntityNotFoundException exception) {
        return List.of(Error.builder()
                .message(exception.getMessage())
                .build());
    }

    private List<ValidationError> buildValidationErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
    }


}