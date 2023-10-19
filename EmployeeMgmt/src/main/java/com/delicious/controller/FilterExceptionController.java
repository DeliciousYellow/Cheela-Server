package com.delicious.controller;


import com.delicious.exception.TokenException;
import com.delicious.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用来接收过滤器出错后重定向过来的请求
 * 到这里再抛给全局异常处理
 */

@RestController
@RequestMapping("/filterException")
public class FilterExceptionController {
    @RequestMapping("/error")
    //从过滤器处被拦截的http请求类型可能是多样的
    public Result throwException(HttpServletRequest request) {
        throw (TokenException) request.getAttribute("TokenException");
    }
}
