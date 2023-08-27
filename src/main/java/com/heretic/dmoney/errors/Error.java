package com.heretic.dmoney.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Builder
@AllArgsConstructor
public class Error {

    private final String message;
    @JsonInclude(NON_NULL)
    private final String fieldName;
}