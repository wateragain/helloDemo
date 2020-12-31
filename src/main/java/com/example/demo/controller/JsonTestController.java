package com.example.demo.controller;

import com.example.demo.dto.base.Result;
import com.example.demo.dto.input.TestInput;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

@RestController
@RequestMapping("testJson")
public class JsonTestController {

    //不是post body的时候走DateFormatterConfig，post body的时候走jackson

    //ZonedDateTime date4必须要带上zoneId信息否则是不行的,而我们在Config里配置后就带不上zoneId了,而且zonedDateTime也不适合作为入参使用
    @GetMapping("1")
    public Result testJson1(Date date1, LocalDate date2, LocalDateTime date3, ZonedDateTime date4) {
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
        return Result.successRet(date1);
    }

    @PostMapping("2")
    public Result testJson2(Date date1, LocalDate date2, LocalDateTime date3, ZonedDateTime date4) {
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println(date4);
        return Result.successRet(date1);
    }

    //jackson默认会把localdatetime序列化为中间带T，所以反序列化（即传入）不配置的要带T才能成功
    @PostMapping("3")
    public Result testJson3(@RequestBody TestInput input) {
        System.out.println(input.getDate1());
        System.out.println(input.getDate2());
        System.out.println(input.getDate3());
        System.out.println(input.getDate4());
        return Result.successRet(input);
    }

    //get直接参数的方式arrayList接收不了
    @GetMapping("4")
    public Result testJson4(String[] a, ArrayList<String> list) {
        System.out.println(list);
        return Result.successRet(a);
    }

    //get方式包成pojo对象就能接收List<String>了
    @GetMapping("5")
    public Result testJson5(TestInput input) {
        return Result.successRet(input);
    }

    @PostMapping("6")
    public Result testJson6(TestInput input) {
        return Result.successRet(input);
    }

    @PostMapping("7")
    public Result testJson7(@RequestBody TestInput input) {
        System.out.println(input.getInner().getStr());
        return Result.successRet(input);
    }

}
