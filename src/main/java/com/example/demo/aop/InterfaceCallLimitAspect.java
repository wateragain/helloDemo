package com.example.demo.aop;

import com.example.demo.annotation.TestAnno;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class InterfaceCallLimitAspect {

    //返回类型             包名(参数..)
    //   *      com.example.demo.controller..*.*(..))  执行com.example.demo.controller包和子包下定义的任何方法
    // ..*代表包和子包,不用子包则直接com.example.demo.controller.*(..)
    @Pointcut("execution(* com.example.demo.controller..*.*(..))")
    public void controllerPoint() {}

    @Before(value = "controllerPoint() && @annotation(anno)")
    public void callLimitBefore(JoinPoint joinPoint, TestAnno anno){

    }

    @Around(value = "controllerPoint()")
    public Object callLimitAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        MethodSignature signature =  (MethodSignature) pjp.getSignature();
        TestAnno annotation = signature.getMethod().getAnnotation(TestAnno.class);
        return pjp.proceed();
    }

}

