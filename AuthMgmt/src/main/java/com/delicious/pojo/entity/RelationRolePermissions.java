package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@Data
@EqualsAndHashCode(callSuper = false)//这个注解用于在生成 equals 和 hashCode 方法时，指示 Lombok 不要考虑父类的字段。
@Builder
@TableName("relation_role_permissions")
public class RelationRolePermissions {

    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private Integer permissionId;

}
