package com.delicious.exception;

import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ErrorException.class)
    @ResponseBody
    public Result handleErrorException(ErrorException e) {
        String stackTrace = exceptionSout(e);
        log.error(stackTrace);
        return Result.build(ResultEnum.ERROR).setMessage(e.getMessage());
    }
    public String exceptionSout(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw+"============================================= 错误已记录 =============================================";
    }

    /**
     * 处理@Validated 注解判断参数不符合定义时抛出的异常
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            stringBuilder.append(error.getDefaultMessage()).append("/n");
        }
        return Result.build(ResultEnum.PARAMETER_ERROR).setMessage(stringBuilder.toString());
    }

    /**
     * 处理@Valid 注解判断参数不符合定义时抛出的异常
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            stringBuilder.append(constraintViolation.getMessageTemplate()).append("/n");
        }
        return Result.build(ResultEnum.PARAMETER_ERROR).setMessage(stringBuilder.toString());
    }
}