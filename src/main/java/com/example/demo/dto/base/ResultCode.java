package com.example.demo.dto.base;

public enum ResultCode implements BaseResultCode{

    SUCCESS("0000", "操作成功"),
    GATEWAY_ERROR("0001", "服务异常"),
    PARAMS_ERROR("0002", "参数错误"),
    HTTP_METHOD_ERROE("0003", "请求方式不支持"),
    UNAUTH_ERROR("0004", "暂无权限访问"),
    SIGN_VERIFY_ERROR("0005", "签名校验失败"),
    FILE_UPLOAD_ERROR("0006", "文件上传失败"),
    ;

    private String msg;
    private String code;

    private ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
