package com.example.demo.feign;

import org.springframework.stereotype.Component;

@Component
public class TestClient {

    public String test(String test){
        return test;
    }
}
