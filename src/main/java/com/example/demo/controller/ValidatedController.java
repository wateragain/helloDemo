package com.example.demo.controller;

import com.example.demo.annotation.TestAnno;
import com.example.demo.dto.base.Result;
import com.example.demo.dto.input.TestInput;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/testValid")
@Validated //加在类上的@Validated可以对post form参数（非对象）的注解进行校验，其他情况无效  @Valid加在类上都无效
public class ValidatedController {

    //@Valid基本相同,除了post body时抛出的异常类是MethodArgumentNotValidException

    //BindException
    @GetMapping("1")
    //get接收对象不能加@RequestParam，会报参数a不存在
    //加在方法上的@Validated无法校验，必须加在参数里
    @TestAnno(period = 555)
    public Result testValid1(@Validated TestInput a){
        return Result.successRet(a);
    }

    //ConstraintViolationException
    @GetMapping("2")
    public Result testValid2(@RequestParam @NotBlank(message = "请输入字符串") String str,
                             @RequestParam Integer num){
        return Result.successRet(str + num);
    }

    //BindException
    @PostMapping("3")
    //加在方法上的@Validated无法校验，必须加在参数里
    public Result testValid3(@Validated TestInput a){
        return Result.successRet(a);
    }

    //@RequestParam->MissingServletRequestParameterException
    @PostMapping("4")
    //加在方法上的@Validated无法校验,加在里面的也不行,要加在类头上
    public Result testValid4(@RequestParam @NotBlank(message = "请输入字符串") String str,
                             @RequestParam Integer num){
        return Result.successRet(str + num);
    }

    //BindException
    @PostMapping("5")
    public Result testValid5(@Validated @RequestBody TestInput a){
        return Result.successRet(a);
    }

    //NumberFormatException
    @GetMapping("er")
    public Result er(String str){
        System.out.println(1/0);
        System.out.println(new Integer(str));
        //throw new RuntimeException();
        return Result.successRet(str);
    }

}
