package com.example.springboot.demo.service.impl;

import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.mapper.LearnSqlMapper;
import com.example.springboot.demo.util.FakerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author guo
 * @date 2021/7/25
 */
@Service
public class TX2Service {

    @Resource
    private LearnSqlMapper sqlMapper;

    @Transactional(propagation = Propagation.NESTED)
    public void innerTXBusiness() {
        List<UserInfo> userInfos = IntStream.rangeClosed(1, 4)
                .mapToObj(i -> new UserInfo(FakerUtil.getRandomId(), FakerUtil.getRandomName()))
                .collect(Collectors.toList());
        userInfos.forEach(System.out::println);
        sqlMapper.insertToUserInfo(userInfos);
        throw new RuntimeException("RuntimeException from outerTXBusiness");
    }
}
