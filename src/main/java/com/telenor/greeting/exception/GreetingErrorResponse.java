package com.telenor.greeting.exception;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class GreetingErrorResponse {
    private String code;
    private String message;
}
