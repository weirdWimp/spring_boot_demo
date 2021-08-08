package com.example.springboot.demo.controller.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author guo
 * @date 2021/8/8
 */
public class UserLoginValidator implements Validator {

    private static final int MINIMUM_PASSWORD_LENGTH = 6;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(LoginUser.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", BaseErrorCode.ARGUMENT_REQUIRED, new Object[]{"username"});
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", BaseErrorCode.ARGUMENT_REQUIRED, new Object[]{"password"});

        LoginUser loginUser = (LoginUser) target;
        if (loginUser.getPassword() != null
                && loginUser.getPassword().trim().length() < MINIMUM_PASSWORD_LENGTH) {
            errors.rejectValue("password", BaseErrorCode.MIN_LENGTH_REQUIRED, new Object[]{"password", MINIMUM_PASSWORD_LENGTH},
                    "The password must be at least [" + MINIMUM_PASSWORD_LENGTH + "] characters in length.");
        }
    }
}
