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
    public void controllerPoint() {
    }

    @Before(value = "controllerPoint() && @annotation(anno)")
    public void callLimitBefore(JoinPoint joinPoint, TestAnno anno) {

    }

    @Around(value = "controllerPoint()")
    public Object callLimitAround(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (var arg : args) {
            //System.out.println(arg);
        }
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        TestAnno annotation = signature.getMethod().getAnnotation(TestAnno.class);
        //在这里处理日志可能会存在问题：请求参数转换错误的异常是在aop的方法外，这里的抛出异常就aop管不到了，根本进不来
        //aop方法里抛出异常，还没到controllerAdvice里，所以无法转换成Result去打印在日志里
        return pjp.proceed();
    }

}

