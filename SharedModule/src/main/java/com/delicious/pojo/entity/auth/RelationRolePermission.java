package com.delicious.pojo.entity.auth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.delicious.pojo.entity.BaseEntity;
import jakarta.validation.constraints.NotNull;
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
@TableName("relation_role_permission")
public class RelationRolePermission extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 1L;
//    @TableId(type = IdType.INPUT)
//    private CompositeKey_RoleId_PermissionId compositeKey;
    @NotNull(message = "角色ID不能为空")
    private Integer roleId;
    @NotNull(message = "权限ID不能为空")
    private Integer permissionId;

}
