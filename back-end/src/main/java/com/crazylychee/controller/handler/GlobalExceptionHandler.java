package com.crazylychee.controller.handler;

import com.crazylychee.result.HttpStatus;
import com.crazylychee.result.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理预期以外的错误
     *
     * @param e 异常
     * @return 统一结果返回
     */
    @ExceptionHandler(Exception.class)
    public JSONResult handleException(Exception e) {
        log.error(e.getMessage(), e);
        return JSONResult.error(HttpStatus.ERROR);
    }



    /**
     * 参数为基本类型时，校验参数异常
     *
     * @param e 异常
     * @return 统一结果返回
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public JSONResult handleConstraintViolationException(ConstraintViolationException e) {
        return JSONResult.error(HttpStatus.BAD_REQUESTS, e.getMessage());
    }


}
