package com.meta.cmm.aop;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TransactionAspect {

    private final PlatformTransactionManager transactionManager;
    private final String EXECUTION = "execution(* com.meta..*.service..*.*(..))";

    /**
     * TransactionInterceptor < 공통 Spring 트랜젝션 인프라를 사용하여 선언적 트랜잭션 관리 재공하는 AOP
     * TransactionAspectSupport 클래스에서 파생되며 API에 대한 필수 호출이 포함되어잇음
     */
    @Bean
    public TransactionInterceptor transactionAdvice(){
        //RollbackRuleAttribute < 주어진 예외가 롤백을 발생시켜야 하는지 여부를 결정하는 규칙, 예외가 발생한 후 트랜잭션을 커밋할지 롤백할지 여부를 결정하기 위한 규칙 <<규칙설정
        List<RollbackRuleAttribute> rollbackRules = Collections.singletonList(new RollbackRuleAttribute(Exception.class));
        //RuleBasedTransactionAttribute < 특정 예외가 양수 및 음수 모두의 여러 롤백 규칙을 저굥ㅇ하여 트랜잭션 롤백을 유발해야하는지 여부 확인 <<여부확인
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setRollbackRules(rollbackRules);
        transactionAttribute.setName("*");

        MatchAlwaysTransactionAttributeSource attributeSource = new MatchAlwaysTransactionAttributeSource();
        attributeSource.setTransactionAttribute(transactionAttribute);

        TransactionInterceptor interceptor = new TransactionInterceptor();
        interceptor.setTransactionManager(transactionManager);
        interceptor.setTransactionAttributeSource(attributeSource);

        return interceptor;

    }


    /***
     * Advisor는 인터페이스의 구현체를 사용해 생성할 수 있습니다.
     * Advisor는 1개의 Pointcut 과 1개의 Advice  묶은 것
     * Pointcut : 어디에 적용할지 판단기준
     * Advice : 프록시가 사용하는 부가기능(로직)
     */
    @Bean
    public Advisor transactionAdvisor(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(EXECUTION);
        //DefaultPointcutAdvisor < Advisor는 생성 
        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }
}
