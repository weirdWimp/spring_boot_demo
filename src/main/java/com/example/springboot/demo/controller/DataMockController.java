package com.example.springboot.demo.controller;

import com.example.springboot.demo.mapper.LearnSqlMapper;
import com.example.springboot.demo.util.FakerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock/data")
public class DataMockController {

    @Autowired
    LearnSqlMapper learnSqlMapper;

    @GetMapping(path = "/user-info")
    public void insertIntoUserInfo(@RequestParam(name = "max", defaultValue = "100") int max,
                                   @RequestParam(name = "threads", defaultValue = "2") int threads,
                                   @RequestParam(name = "commit", defaultValue = "50") int commit) {
        FakerUtil.insert(max, threads, commit, learnSqlMapper);
    }
}
