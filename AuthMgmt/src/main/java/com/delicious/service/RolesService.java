package com.delicious.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.delicious.pojo.entity.Permission;
import com.delicious.pojo.entity.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
public interface RolesService extends IService<Role> {
    List<Permission> QueryPermissionsByUserID(Integer id);
}
