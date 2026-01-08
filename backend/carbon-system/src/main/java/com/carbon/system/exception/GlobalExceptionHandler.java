package com.carbon.system.exception;

import com.carbon.system.common.ApiCode;
import com.carbon.system.common.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理数据库访问异常
     */
    @ExceptionHandler(DataAccessException.class)
    public ApiResult<?> handleDataAccessException(DataAccessException e) {
        log.error("数据库访问异常", e);
        return ApiResult.fail(ApiCode.INTERNAL_SERVER_ERROR.getCode(), "数据库访问异常: " + e.getMessage());
    }

    /**
     * 处理空指针异常
     */
    @ExceptionHandler(NullPointerException.class)
    public ApiResult<?> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常", e);
        return ApiResult.fail(ApiCode.INTERNAL_SERVER_ERROR.getCode(), "系统错误: 空指针异常");
    }

    /**
     * 处理非法参数异常
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResult<?> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("参数异常", e);
        return ApiResult.fail(ApiCode.FAIL.getCode(), "参数错误: " + e.getMessage());
    }

    /**
     * 处理运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ApiResult<?> handleRuntimeException(RuntimeException e) {
        log.error("运行时异常", e);
        return ApiResult.fail(ApiCode.INTERNAL_SERVER_ERROR.getCode(), "系统错误: " + e.getMessage());
    }

    /**
     * 处理所有其他异常
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<?> handleException(Exception e) {
        log.error("系统异常", e);
        return ApiResult.fail(ApiCode.INTERNAL_SERVER_ERROR.getCode(), "系统错误: " + e.getMessage());
    }
}

