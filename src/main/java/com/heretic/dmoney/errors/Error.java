package com.heretic.dmoney.errors;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Error {

    private final String message;
}