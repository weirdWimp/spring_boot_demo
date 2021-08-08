package com.example.springboot.demo.service.impl;

import com.example.springboot.demo.entity.UserInfo;
import com.example.springboot.demo.mapper.LearnSqlMapper;
import com.example.springboot.demo.util.FakerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author guo
 * @date 2021/7/25
 */
@Service
@Slf4j
public class TransactionTestService {

    @Resource
    private LearnSqlMapper sqlMapper;

    @Resource
    private PlatformTransactionManager transactionManager;

    @Transactional(propagation = Propagation.REQUIRED)
    public void outerTXBusiness() {
        UserInfo userInfo = new UserInfo(FakerUtil.getRandomId(), FakerUtil.getRandomName());
        System.out.println(userInfo);
        sqlMapper.insertToUserInfo(Collections.singletonList(userInfo));

        innerTXBusiness();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void innerTXBusiness() {
        System.out.println("innerTXBusiness:" + this.getClass());
        List<UserInfo> userInfos = IntStream.rangeClosed(1, 4)
                .mapToObj(i -> new UserInfo(FakerUtil.getRandomId(), FakerUtil.getRandomName()))
                .collect(Collectors.toList());
        userInfos.forEach(System.out::println);
        sqlMapper.insertToUserInfo(userInfos);
        throw new RuntimeException("hello");
    }

    public void outerBiz1() {
        UserInfo userInfo = new UserInfo(FakerUtil.getRandomId(), FakerUtil.getRandomName());
        sqlMapper.insertToUserInfo(Collections.singletonList(userInfo));
    }

    public void outerBiz2() {
        throw new BadSqlGrammarException("task", "sql", null);
    }

    public void innerBiz() {
        List<UserInfo> userInfos = IntStream.rangeClosed(1, 4)
                .mapToObj(i -> new UserInfo(FakerUtil.getRandomId(), FakerUtil.getRandomName()))
                .collect(Collectors.toList());
        userInfos.forEach(System.out::println);
        sqlMapper.insertToUserInfo(userInfos);
        // throw new BadSqlGrammarException("task", "sql", null);
    }

    /**
     * 通过显示的代码来控制事务的执行，来验证事务的传播行为
     */
    public void nestedTXTest() {
        TransactionDefinition outerTXDef = createTXDefinition("OuterTX", TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus outerStatus = transactionManager.getTransaction(outerTXDef);
        try {
            txExecute(outerStatus, () -> {
                outerBiz1();

                innerTXOperation();

                outerBiz2();
            });
        } catch (Exception e) {
            log.error("OuterTX exception", e);
        }
    }

    private void innerTXOperation() {
        TransactionDefinition innerTXDef = createTXDefinition("InnerTX", TransactionDefinition.PROPAGATION_NESTED);
        TransactionStatus innerStatus = transactionManager.getTransaction(innerTXDef);
        try {
            innerBiz();
        } catch (Exception e) {
            log.error(e.getMessage() + " from innerTXOperation");
            transactionManager.rollback(innerStatus);
            return;
        }
        // 当调用了 commit 后，如果该事务不是一个新事务，则会忽略提交以正确参与周围的事务中
        transactionManager.commit(innerStatus);
    }

    @FunctionalInterface
    interface TXOperation {
        void apply();
    }

    public void txExecute(TransactionStatus status, TXOperation txOperations) throws Exception {
        try {
            txOperations.apply();
        } catch (Exception ex) {
            transactionManager.rollback(status);
            throw ex;
        }

        // 当调用了 commit 后，如果该事务不是一个新事务，则会忽略提交以正确参与周围的事务中
        // 所以按照模板式的使用就可以了，不必关心如何参与到已经存在的事务中，只需要定义正确的 TransactionDefinition (包括传播行为，隔离级别等)
        transactionManager.commit(status);
    }

    public TransactionDefinition createTXDefinition(String name, int propagation) {
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setName(name);
        definition.setPropagationBehavior(propagation);
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        return definition;
    }
}
