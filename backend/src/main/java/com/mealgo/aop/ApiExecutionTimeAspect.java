package com.mealgo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ApiExecutionTimeAspect {

    @Around("within(com.mealgo.controller..*)")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info(
                "API Execute Time | method={} | time={} ms",
                joinPoint.getSignature().toShortString(),
                end - start);

        return result;
    }
}