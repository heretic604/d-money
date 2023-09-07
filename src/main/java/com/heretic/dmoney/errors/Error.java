package com.heretic.dmoney.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Builder
public record Error(String message, @JsonInclude(NON_NULL) String fieldName) {
}