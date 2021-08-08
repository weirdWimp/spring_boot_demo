package com.example.springboot.demo.controller.validation;

import com.example.springboot.demo.entity.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * @author guo
 * @date 2021/8/8
 */
@RestController
@RequestMapping("/javaBeanValidation")
@Slf4j
public class JavaBeanValidationController implements MessageSourceAware {

    private MessageSource messageSource;

    @RequestMapping(value = "validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse<String> handle(@RequestBody @Valid JavaBeanLoginUser loginUser, Errors errors) {
        if (errors.hasErrors()) {
            ObjectError objectError = errors.getAllErrors().get(0);
            String code = objectError.getCode();

            String[] codes = objectError.getCodes();
            for (String codeStr : codes) {
                System.out.println("#### code:" + codeStr);
            }

            Object[] arguments = objectError.getArguments();
            for (Object argument : arguments) {
                System.out.println("#### argument - type:" + argument.getClass() + ", value:" + argument);
            }
            System.out.println("#### defaultMessage:" + objectError.getDefaultMessage());

            String errMsg = objectError.getDefaultMessage();
            return SimpleResponse.fail(objectError.getCode(), errMsg);
        }
        return SimpleResponse.success("ok");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
