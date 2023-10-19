package com.delicious.exception;

import com.delicious.pojo.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PermissionDeniedException extends RuntimeException{
    private ResultEnum resultEnum;

    public PermissionDeniedException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.resultEnum = resultEnum;
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }


}
