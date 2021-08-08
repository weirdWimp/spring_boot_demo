package com.example.springboot.demo.controller.validation;

import com.example.springboot.demo.entity.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.text.MessageFormat;
import java.util.Properties;

/**
 * @author guo
 * @date 2021/8/8
 */
@RestController
@RequestMapping("/validation")
@Slf4j
public class SpringValidationController {

    @Resource
    private Properties errTableProperties;

    /**
     * Add custom UserLoginValidator
     *
     * @param binder
     */
    @InitBinder
    public void init(WebDataBinder binder) {
        binder.addValidators(new UserLoginValidator());
    }

    @RequestMapping(value = "validate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleResponse<String> handle(@RequestBody @Valid LoginUser loginUser, Errors errors) {
        if (errors.hasErrors()) {
            ObjectError objectError = errors.getAllErrors().get(0);
            String code = objectError.getCode();
            String errMsg;
            if (code != null && !errTableProperties.contains(code)) {
                errMsg = MessageFormat.format(errTableProperties.getProperty(code), objectError.getArguments());
            } else {
                log.error("code {} is not found in default error properties file", code);
                errMsg = MessageFormat.format(errTableProperties.getProperty(BaseErrorCode.SYSTEM_ERROR), objectError.getArguments());
            }
            return SimpleResponse.fail(objectError.getCode(), errMsg);
        }
        return SimpleResponse.success("ok");
    }
}
