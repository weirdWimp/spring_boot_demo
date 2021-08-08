package com.example.springboot.demo.service.impl;

import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.mapper.LearnSqlMapper;
import com.example.springboot.demo.util.FakerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @author guo
 * @date 2021/7/25
 */
@Service
@Slf4j
public class TX1Service {

    @Resource
    private LearnSqlMapper sqlMapper;

    @Resource
    private TX2Service tx2Service;

    @Transactional(propagation = Propagation.REQUIRED)
    public void outerTXBusiness() {
        UserInfo userInfo = new UserInfo(FakerUtil.getRandomId(), FakerUtil.getRandomName());
        sqlMapper.insertToUserInfo(Collections.singletonList(userInfo));
        try {
            tx2Service.innerTXBusiness();
        } catch (Exception e) {
            log.error("e from innerTXBusiness:" + e.getMessage());
        }
        // throw new RuntimeException("RuntimeException from outerTXBusiness");
    }
}
