package com.huuuge.demojug.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@Validated
public class HelloController {
    private static final int MAX_CITY_LENGTH = 15;
    private static final int MIN_CITY_LENGTH = 3;
    @GetMapping("/greeting")
    public String greeting(@Value("${app.returned-string}") String someString) {
        return someString;
    }

    @GetMapping("/greeting/{city}")
    public String greetingForCity(@Valid @Size(min = MIN_CITY_LENGTH, max = MAX_CITY_LENGTH, message = "City length must be between 3-15") @PathVariable String city) {
        return "Hello " + city + "!";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintValidationException(ConstraintViolationException constraintViolationException){
        return constraintViolationException.getMessage();
    }
}
