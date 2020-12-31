package com.example.demo.dto.base;

import lombok.Data;

@Data
public class RequestLog {
    /**
     * 服务名
     */
    private String serverName;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求方法
     */
    private String requestMethod;
    /**
     * 请求表单
     */
    private String requestForm;

    /**
     * 请求的body
     */
    private String requestBody;
    /**
     * 返回的状态
     */
    private int responseStatus;
    /**
     * 返回的body
     */
    private String responseBody;
    /**
     * 耗时
     */
    private double costTime;
    /**
     * 请求头
     */
    private String requestHeaders;
}
