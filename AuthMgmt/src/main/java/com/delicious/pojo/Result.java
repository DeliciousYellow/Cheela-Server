package com.delicious.pojo;


import lombok.Builder;
import lombok.Getter;

/**
 * @program: ES-furniture
 * @description:
 * @author: 王炸！！
 * @create: 2023-04-13 22:51
 **/
@Getter
public class Result<T> {
    //    private ResultEnum resultEnum;
    private Integer code;
    private String message;
    private T data;

    private Result(ResultEnum resultEnum, T data) {
        if (resultEnum!=null){
            this.code = resultEnum.getCode();
            this.message = resultEnum.getMessage();
        }
        this.data = data;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public static <S> Result<S> build(ResultEnum resultEnum) {
        return new Result<>(resultEnum, null);
    }

    public static <S> Result<S> build(ResultEnum resultEnum, S data) {
        return new Result<>(resultEnum, data);
    }
}
