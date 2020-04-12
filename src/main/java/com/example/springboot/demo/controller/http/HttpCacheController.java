package com.example.springboot.demo.controller.http;

import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RequestMapping("/cache")
@Controller
public class HttpCacheController {

    /**
     * The example sends an 304 (NOT_MODIFIED) response with an empty body
     * if the comparison to the conditional request headers indicates that the content has not changed.
     * Otherwise, the ETag and Cache-Control headers are added to the response.
     *
     * 条件化请求不满足时（ETag 或者 LastModified 条件），返回 304， 表示资源未修改
     */
    @GetMapping
    public ResponseEntity<String> cache() {
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .eTag("1")
                .lastModified(new Date().getTime())
                .body("Conditional Request return entity");
    }

}
