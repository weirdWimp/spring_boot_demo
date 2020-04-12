package com.example.springboot.demo.controller.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.security.provider.MD5;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Controller
@RequestMapping(value = "/validation")
public class ValidationController {

    /**
     * http BASIC 认证， 认证用户id和密码通过(:)冒号连接，经Base64编码后，发送给服务器；用户代理为浏览器时，会自动完成Base64的编码转换
     * 可通过浏览器进行测试，验证用户信息 guest:guest
     *
     * 安全性等级低，没有严格意义上的加密，Base64只是编码，任何人都可以进行解码拿到认证信息
     */
    @RequestMapping("basic")
    public ResponseEntity basicValidate(@RequestHeader("Authorization") Optional<String> validateInfo) {
        System.out.println("validateInfo = " + validateInfo);
        if (!validateInfo.isPresent() || !"Basic Z3Vlc3Q6Z3Vlc3Q=".equals(validateInfo.get())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Input your ID and password\"")
                    .build();
        }

        final MD5 md5 = new MD5();
        final byte[] decodeBytes = Base64.getDecoder().decode(validateInfo.get().split("\\s")[1].getBytes(StandardCharsets.UTF_8));
        System.out.println("basic validation info: " + new String(decodeBytes, StandardCharsets.UTF_8));
        return ResponseEntity.ok("Authorization Success");
    }

}
