package com.delicious.exception.Handler;

import com.delicious.exception.CallServiceException;
import com.delicious.exception.ErrorException;
import com.delicious.exception.LoginFailureException;
import com.delicious.exception.TokenException;
import com.delicious.pojo.Result;
import com.delicious.pojo.ResultEnum;
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
    /**
     * 处理@Validated注解判断参数不符合定义时抛出的异常
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuffer stringBuffer = new StringBuffer();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            stringBuffer.append(error.getDefaultMessage()).append("/n");
        }
        return Result.build(ResultEnum.PARAMETER_ERROR).setMessage(stringBuffer.toString());
    }

    /**
     * 处理   LoginFailureException   异常
     * @return Result
     */
    @ExceptionHandler(LoginFailureException.class)
    @ResponseBody
    public Result handleLoginFailureException(LoginFailureException e) {
        return Result.build(ResultEnum.LOGIN_FAIL).setMessage(e.getMessage());
    }

    /**
     * 处理   CallServiceException   异常
     * @return Result
     */
    @ExceptionHandler(CallServiceException.class)
    @ResponseBody
    public Result handleLoginFailureException(CallServiceException e) {
        return Result.build(ResultEnum.CALL_ERROR).setMessage(e.getMessage());
    }

    /**
     * 处理   TokenException   异常
     * @return Result
     */
    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public Result handleTokenException(TokenException e) {
        return Result.build(e.getResultEnum());
    }








    @ExceptionHandler(ErrorException.class)
    @ResponseBody
    public Result handleErrorException(ErrorException e) {
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