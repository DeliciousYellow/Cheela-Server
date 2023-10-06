package com.delicious.pojo.entity;

import com.delicious.exception.ErrorException;

import java.io.Serializable;
import java.lang.reflect.Field;

public abstract class BaseEntity implements Serializable {
    public boolean IsNull(){
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getName().equals("serialVersionUID")){
                continue;
            }
            Object value = null;
            try {
                field.setAccessible(true);
                value = field.get(this);
            } catch (IllegalAccessException e) {
                throw new ErrorException(e);
            }
            if (value!=null){
                if (value instanceof BaseEntity){
                    if (!((BaseEntity) value).IsNull()){
                        return false;
                    }
                }
                return false;
            }
            field.setAccessible(false);
        }
        return true;
    }
}
