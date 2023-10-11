package com.delicious.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

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
@TableName("relation_role_permissions")
public class RelationRolePermission {

    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private Integer permissionId;

}
