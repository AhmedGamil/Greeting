package com.telenor.greeting.controller;

import com.telenor.greeting.pojo.BusinessType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class GreetingController {

    public static final String PERSONAL_GREETING_MESSAGE = "Hi, userId %s";
    public static final String BUSINESS_GREETING_MESSAGE = "Welcome, business user!";
    public static final String ID_NOT_VALID_ERROR_MESSAGE = "Id should be positive integer";

    @GetMapping(path = "/ping")
    public ResponseEntity<Void> ping() {
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/greeting", params = {"account=personal", "id"})
    public String greetingPersonal(@RequestParam(name = "id")
                                   @Min(value = 1, message = ID_NOT_VALID_ERROR_MESSAGE) int id) {
        return String.format(PERSONAL_GREETING_MESSAGE, id);
    }

    @GetMapping(path = "/greeting", params = {"account=business", "type"})
    public String greetingBusiness(@RequestParam(name = "type") @NotNull BusinessType type) {

        if (BusinessType.SMALL.equals(type)) {
            throw new UnsupportedOperationException("not yet implemented");
        }
        return BUSINESS_GREETING_MESSAGE;
    }

}
