package com.example.springboot.demo.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * @author guo
 * @date 2021/8/4
 */
@Controller
@RequestMapping("/redirect")
public class RedirectController {

    @Data
    @AllArgsConstructor
    static class JsonBody {
        private String name;
        private String description;
    }

    /**
     * Why POST method return a 302 Response and GET just works fine?
     *
     * @param redirectAttributes
     * @return
     */
    @GetMapping("original")
    public String deprecatedServiceMethod(RedirectAttributes redirectAttributes) {
        JsonBody jsonBody = new JsonBody("Jason", "This is Jason");
        redirectAttributes.addFlashAttribute("jsonBody", jsonBody);
        return "redirect:new";
    }

    @ResponseBody
    @GetMapping(value = "new", produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonBody service(Model model) {
        Object jsonBody = model.getAttribute("jsonBody");
        return (JsonBody) jsonBody;
    }
}
