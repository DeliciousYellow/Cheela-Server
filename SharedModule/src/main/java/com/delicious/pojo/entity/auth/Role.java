package com.delicious.pojo.entity.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.delicious.pojo.entity.BaseEntity;
import lombok.*;

import java.io.Serial;

/**
 * <p>
 *
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;
    private String roleName;
    private String roleDescription;


}
