package com.example.springboot.demo.controller.basic;

import com.example.springboot.demo.entity.Person;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Objects;

@Controller
@RequestMapping
public class BasicRequestController {

    @GetMapping(path = "user/{userId}/home", params = "wvr=5", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String detail(@PathVariable String userId, @RequestParam(value = "wvr") int wvrNum) {
        return userId + " " + wvrNum;
    }

    @GetMapping(path = "${external.base.path.uri}/spring-framework-reference")
    @ResponseBody
    public String baseUriFromPropertySource() {
        return "ok";
    }


    @GetMapping(path = "person", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Person getPerson() {
        return new Person("Jason", 26, "male", "AVE.18");
    }


    @RequestMapping(value = "addPerson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addPerson(@RequestBody @Valid Person person, Errors errors) {
        if (errors.hasErrors()) {
            for (ObjectError objectError : errors.getAllErrors()) {
                String errStr = String.join("\n", Objects.requireNonNull(objectError.getCodes()));
                System.out.println("errStr = " + errStr);
            }
            return "fail";
        }
        return "ok";
    }

    @GetMapping("{name}/{age}")
    @ResponseBody
    public Person getPersonVar(@ModelAttribute(binding = true) Person person) {
        return person;
    }

    @GetMapping("{localDate}")
    @ResponseBody
    public LocalDate getDate(@ModelAttribute("localDate") LocalDate date) {
        return date;
    }

    @GetMapping("date/{localDate}")
    @ResponseBody
    public LocalDate getPersonVar2(@PathVariable("localDate") LocalDate date) {
        return date;
    }
}
