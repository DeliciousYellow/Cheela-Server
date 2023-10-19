package com.delicious.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.delicious.pojo.entity.auth.Permission;
import com.delicious.pojo.entity.auth.Role;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
public interface RolesMapper extends BaseMapper<Role> {
    List<Permission> QueryPermissionsByUserID(Integer id);

    List<Permission> QueryPermissionsByRoleID(Integer id);

    List<Role> QueryRolesByUserID(Integer id);
}
