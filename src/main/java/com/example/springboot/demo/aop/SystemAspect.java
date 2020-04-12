package com.example.springboot.demo.aop;

import com.example.springboot.demo.entity.Person;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
public class SystemAspect {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Match the execution of any public method
     */
    @Pointcut("execution(public * *(..))")
    public void anyPublicOperation() {
    }

    /**
     * Match the execution of any method in service and subpackage
     */
    @Pointcut("within(com.example.springboot.demo.service..*)")
    public void withServiceAndSubPackage() {
    }

    /**
     *
     * Limits matching to join points (the execution of methods when using Spring AOP)
     * where the arguments are instances of the given types - java.io.Serializable
     *
     * The alternative way is to declare parameter pattern in execution designator, like:
     * `execution(public * *(java.io.Serializable)) `
     */
    @Pointcut("args(java.io.Serializable)")
    public void limitArgsToSerializable() {
    }


    // ---------------------------------- Advice ----------------------------------


    /**
     * Before advice for any method in bean named `DataDealService`
     */
    @Before("withServiceAndSubPackage() && bean(DataDealService)")
    public void dataDealServiceBeforeAdvice() {
        logger.info("Before Advice Executing - DataDealService");
    }

    /**
     * Before advice for any method named `deal` with any number of parameters and any return type
     */
    @Before("withServiceAndSubPackage() && execution(* deal(..)) && @annotation(transactional)")
    public void serviceBeforeAdvice(Transactional transactional) {
        logger.info("Before Advice Executing - deal method in service module, @Transactional: " + transactional.propagation());
    }

    @Before(value = "execution(* com.example.springboot.demo.service.impl.DataDealService.print(..)) && args(info)")
    public void beforeAdviceWithParameter(Person info) {
        logger.info("Before Advice Executing - get Person " + info);
    }
}
