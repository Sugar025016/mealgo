package com.mealgo.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ApiLogAspect {

    @AfterReturning(pointcut = "within(com.mealgo.controller..*)", returning = "result")
    public void logSuccess(
            JoinPoint joinPoint,
            Object result) {

        log.info(
                "API Success | method={} | args={} | result={}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()),
                result);
    }

    @AfterThrowing(pointcut = "within(com.mealgo.controller..*)", throwing = "ex")
    public void logError(
            JoinPoint joinPoint,
            Exception ex) {

        log.error(
                "API Error | method={} | args={} | message={}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()),
                ex.getMessage(),
                ex);
    }
}