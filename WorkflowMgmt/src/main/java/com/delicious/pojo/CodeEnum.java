package com.delicious.pojo;

public enum CodeEnum {

    //token相关
    TOKEN_TIMEOUT(350), TOKEN_ERROR(351),

    //digest相关
    DIGEST_ERROR(360);


    public final Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }
}
