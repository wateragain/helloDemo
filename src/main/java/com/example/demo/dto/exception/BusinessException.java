package com.example.demo.dto.exception;

import com.example.demo.dto.base.BaseResultCode;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private BaseResultCode code;

    private String msg;

    public BusinessException() {
    }

    public BusinessException(BaseResultCode errorCode) {
        this.code = errorCode;
        this.msg = errorCode.getMsg();
    }

    public BusinessException(BaseResultCode errorCode, String msg) {
        this.code = errorCode;
        this.msg = msg;
    }
}
