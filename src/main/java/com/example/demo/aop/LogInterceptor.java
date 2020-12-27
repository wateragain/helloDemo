package com.example.demo.aop;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Configuration
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println(request.getRequestURI());
        System.out.println(request.getMethod());
        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements()){
            String headerKey=headers.nextElement();
            System.out.println(headerKey + ":" + request.getHeader(headerKey) + "\t");
        }
        System.out.println(request.getReader().readLine());

        //System.out.println(request.getQueryString());

    }

}
