package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.delicious.pojo.AddAndEditGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serial;

/**
 * @author 黄灿
 * @since 2023-09-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Builder
@TableName("t_user")
public class User extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @NotNull(message = "手机号不能为空", groups = {AddAndEditGroup.class})
    @Pattern(regexp = "^(\\d{11})$", message = "手机号必须是11位数字", groups = {AddAndEditGroup.class})
    private String userPhone;

    @NotNull(message = "用户名不能为空", groups = {AddAndEditGroup.class})
    @Size(min = 1, max = 50, message = "用户名长度必须在1到50之间", groups = {AddAndEditGroup.class})
    private String userName;

    @NotNull(message = "用户密码不能为空", groups = {AddAndEditGroup.class})
    @Size(min = 1, max = 255, message = "密码长度必须在1到255之间", groups = {AddAndEditGroup.class})
    private String userPassword;

    @Size(max = 255, message = "用户头像链接长度不能超过255", groups = {AddAndEditGroup.class})
    private String userAvatar;

    @Size(max = 255, message = "用户邮箱长度不能超过255", groups = {AddAndEditGroup.class})
    @Email(message = "用户邮箱格式不正确", groups = {AddAndEditGroup.class})
    private String userEmail;

    @NotNull(message = "逻辑删除字段不能为空", groups = {AddAndEditGroup.class})
    @TableLogic
    private Integer logicallyDelete;
}
