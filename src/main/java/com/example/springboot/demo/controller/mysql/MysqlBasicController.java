package com.example.springboot.demo.controller.mysql;


import com.example.springboot.demo.mapper.LearnSqlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@RestController
@RequestMapping("/mysql")
public class MysqlBasicController {

    @Resource
    LearnSqlMapper learnSqlMapper;

    @GetMapping("/timestamp")
    public void setTimestampTest(@RequestParam("timeStr") String timestamp) {
        // SimpleDateFormat sdf_8 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // sdf_8.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // System.out.println("GMT+8:00 = " + sdf_8.format(timestamp));
        // learnSqlMapper.insertIntoTime(timestamp);
    }

    @GetMapping("/get-timestamp")
    public void getTimestampTest() {
        List<Map<String, Object>> rows = learnSqlMapper.getRow();
        for (Map<String, Object> row : rows) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                System.out.println("entry.getValue() = " + entry.getValue());
            }
        }
    }

}
