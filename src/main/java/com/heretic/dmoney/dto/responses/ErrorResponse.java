package com.heretic.dmoney.dto.responses;

import com.heretic.dmoney.errors.Error;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {

    private int errorsCount;
    private List<Error> errors;
    private LocalDateTime time;
    private HttpStatus httpStatus;
}