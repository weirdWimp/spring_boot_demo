package com.example.springboot.demo.validation;

import com.example.springboot.demo.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "age", "empty age");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "empty gender");
    }
}
