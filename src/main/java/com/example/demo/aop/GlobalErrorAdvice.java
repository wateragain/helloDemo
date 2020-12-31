package com.example.demo.aop;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.example.demo.dto.base.Result;
import com.example.demo.dto.base.ResultCode;
import com.example.demo.dto.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.naming.AuthenticationException;
import javax.validation.ConstraintViolationException;
import java.security.SignatureException;
import java.text.ParseException;
import java.util.List;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalErrorAdvice {

    private static final String validatedMsg = "参数校验错误";

    /**
     * Validated Valid 参数校验异常
     */
    @ExceptionHandler({BindException.class, MethodArgumentNotValidException.class})
    public Result validatedExceptionHandler(Exception ex) {
        List<ObjectError> errors;
        if (ex instanceof BindException) {
            errors = ((BindException) ex).getBindingResult().getAllErrors();
        } else {
            errors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
        }
        StringBuilder msgBuilder = new StringBuilder(validatedMsg);
        for (ObjectError object : errors) {
            msgBuilder.append(object.getDefaultMessage()).append(",");
        }
        log.info(msgBuilder.toString());
        return Result.failRet(ResultCode.PARAMS_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Result constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.info(ExceptionUtil.stacktraceToString(ex));
        return Result.failRet(ResultCode.PARAMS_ERROR);
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public Result httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        return Result.failRet(ResultCode.HTTP_METHOD_ERROE);
    }

    /**
     * 请求地址不存在
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public Result noHandlerFoundExceptionExceptionHandler(NoHandlerFoundException ex) {
        return Result.failRet(ResultCode.HTTP_METHOD_ERROE, "请求地址不存在");
    }

    /**
     * RequestParam->参数未传异常
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public Result missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        //ex.printStackTrace();
        log.info(ex.getParameterName() + "参数未传");
        return Result.failRet(ResultCode.PARAMS_ERROR);
    }

    /**
     * 参数类型错误 如int传了字符串
     * 参数转换错误 如date类型奇怪的格式
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public Result methodArgumentTypeMismatchExceptionHandler(Exception ex) {
        log.info(ExceptionUtil.stacktraceToString(ex));
        return Result.failRet(ResultCode.PARAMS_ERROR, "参数类型错误");
    }

    //以下是开发时遇见的一些异常

    /**
     * 文件过大异常
     */
    @ExceptionHandler({MaxUploadSizeExceededException.class})
    public Result maxUploadSizeExceededExceptionHandler(MaxUploadSizeExceededException ex) {
        return Result.failRet(ResultCode.FILE_UPLOAD_ERROR, ResultCode.FILE_UPLOAD_ERROR + ":文件过大");
    }

    /**
     * 格式转换错误异常
     */
    @ExceptionHandler({NumberFormatException.class, ParseException.class})
    public Result numberFormatExceptionHandler(Exception ex) {
        log.info(ExceptionUtil.stacktraceToString(ex));
        return Result.failRet(ResultCode.PARAMS_ERROR, "参数转换失败");
    }


    /**
     * 签名校验失败异常
     */
    @ExceptionHandler(SignatureException.class)
    public Result signatureExceptionHandler(Exception ex) {
        log.error(ExceptionUtil.stacktraceToString(ex));
        return Result.failRet(ResultCode.SIGN_VERIFY_ERROR);
    }

    /**
     * 未授权异常
     * TokenExpiredException.class
     */
    @ExceptionHandler({AuthenticationException.class})
    public Result unauthenticatedExceptionHandler(Exception ex) {
        return Result.failRet(ResultCode.UNAUTH_ERROR, ex.getMessage());
    }

    /**
     * 自定义异常
     */
    @ExceptionHandler({BusinessException.class})
    public Result businessExceptionHandler(BusinessException ex) {
        return Result.failRet(ex.getCode(), ex.getMsg());
    }

    /**
     * 全局异常
     */
    @ExceptionHandler({Throwable.class})
    public Result throwableHandler(Throwable ex) {
        log.error(ExceptionUtil.stacktraceToString(ex));
        return Result.failRet(ResultCode.GATEWAY_ERROR);
    }
}
