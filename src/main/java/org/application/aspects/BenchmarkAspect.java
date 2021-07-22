package org.application.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class BenchmarkAspect {
    @Before("execution(* org.application.service.UserStorageService.*(..))")
    public void anyApiCallBefore() {
        System.out.println("___________ASPECT CALL BEFORE______________");
    }

    @After("execution(* org.application.service.UserStorageService.*(..))")
    public void anyApiCallAfter(JoinPoint joinPoint) {
        System.out.println("___________ASPECT CALL AFTER_______________");
    }

    @Around("@annotation(LogThisExecutionTime)")
    public Object logThisExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        System.out.println("logging: " + joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }
}
