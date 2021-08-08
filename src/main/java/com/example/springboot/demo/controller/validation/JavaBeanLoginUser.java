package com.example.springboot.demo.controller.validation;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

/**
 * @author guo
 * @date 2021/8/8
 */
@Data
public class JavaBeanLoginUser {

    @NotEmpty(message = "{not.empty}")
    private String username;

    @NotEmpty
    @Length(min = 8, max = 15)
    private String password;

}
