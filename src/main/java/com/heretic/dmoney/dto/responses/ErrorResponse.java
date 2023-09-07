package com.heretic.dmoney.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.heretic.dmoney.errors.Error;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
public class ErrorResponse {

    @JsonInclude(NON_NULL)
    private Integer errorCount;
    @JsonInclude(NON_NULL)
    private List<Error> errors;
    @JsonInclude(NON_NULL)
    private String message;
    private LocalDateTime time;
    private HttpStatus httpStatus;
}