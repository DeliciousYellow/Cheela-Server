package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.security.Permissions;

/**
 * @author 黄灿
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName("t_user")
public class User extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "user_id", type = IdType.AUTO)
    @NotNull
    private Integer userId;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^(\\d{11})$", message = "手机号必须是11位数字")
    private String userPhone;

    @NotNull(message = "用户名不能为空")
    @Size(min = 1, max = 50, message = "用户名长度必须在1到50之间")
    private String userName;

    @NotNull(message = "用户密码不能为空")
    @Size(min = 1, max = 255, message = "密码长度必须在1到255之间")
    private String userPassword;

    @Size(max = 255, message = "用户头像链接长度不能超过255")
    private String userAvatar;

    @Email(message = "用户邮箱格式不正确")
    @Size(max = 255, message = "用户邮箱长度不能超过255")
    private String userEmail;

    @NotNull(message = "逻辑删除字段不能为空")
    private Integer logicallyDelete;

    @TableField(exist = false)
    private Permissions permissions;
}
