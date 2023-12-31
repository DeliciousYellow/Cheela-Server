package com.delicious.exception;

import com.delicious.pojo.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TokenException extends RuntimeException{
    private ResultEnum resultEnum;

    public TokenException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }


}
