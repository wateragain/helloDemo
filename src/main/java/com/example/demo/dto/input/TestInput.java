package com.example.demo.dto.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
public class TestInput {

    @NotBlank(message = "请输入字符串")
    private String str;

    private Integer num;

    private Date date1;

    private LocalDate date2;

    private LocalDateTime date3;

    private ZonedDateTime date4;

    private List<String> list;

    //单个参数的情况下内部类不用加static
    private InnerClass inner;

    //如果包装成数组(list or [])那么要加static
    private List<InnerClass2> innerList;

    //使用public外部才能去到内部类里的参数
    @Data
    public class InnerClass{
        private String str;
    }

    @Data
    public static class InnerClass2{
        private String str;
    }
}
