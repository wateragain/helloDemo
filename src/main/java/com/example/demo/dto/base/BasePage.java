package com.example.demo.dto.base;

import lombok.Data;

@Data
public class BasePage {

    protected int page = 1;

    protected int limit = 20;
}
