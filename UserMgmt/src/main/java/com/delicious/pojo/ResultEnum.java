package com.delicious.pojo;

import lombok.Getter;

@Getter
public enum ResultEnum {

    INSERT_SUCCESS(220,"插入成功"),
    DELETE_SUCCESS(230,"删除成功"),
    UPDATE_SUCCESS(240,"修改成功"),
    SELECT_SUCCESS(210,"查询成功"),

    INSERT_FAIL(520,"插入失败"),
    DELETE_FAIL(530,"删除失败"),
    UPDATE_FAIL(540,"修改失败"),
    SELECT_FAIL(510,"查询失败"),

    ERROR(599,"发生错误");
    private final Integer code;
    private final String message;
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
