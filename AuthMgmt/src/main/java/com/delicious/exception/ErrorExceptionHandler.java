package com.delicious.exception;

import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
@Slf4j
public class ErrorExceptionHandler {
    @ExceptionHandler(ErrorException.class)
    @ResponseBody
    public Result handleCustomException(ErrorException e) {
        //TODO 统一异常处理应该更加细分
        String stackTrace = exceptionSout(e);
        log.error(stackTrace);
        return Result.build(ResultEnum.ERROR).setMessage(stackTrace);
    }

    public String exceptionSout(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw+"============================================= 错误已记录 =============================================";
    }
}