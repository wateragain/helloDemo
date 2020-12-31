package com.example.demo.dto.base;

import lombok.Data;

@Data
public class Result<T> {

    private String code;

    private String msg;

    private T data;

    public static <T> Result<T> successRet(T data) {
        Result<T> res = new Result<T>();
        res.setCode(ResultCode.SUCCESS.getCode());
        res.setMsg(ResultCode.SUCCESS.getMsg());
        res.setData(data);
        return res;
    }

    public static Result failRet(BaseResultCode code) {
        Result res = new Result();
        res.setCode(code.getCode());
        res.setMsg(code.getMsg());
        return res;
    }

    public static Result failRet(BaseResultCode code, String msg) {
        Result res = new Result();
        res.setCode(code.getCode());
        res.setMsg(msg);
        return res;
    }

    public boolean ifSuccess() {
        return ResultCode.SUCCESS.getCode().equals(this.getCode());
    }
}
