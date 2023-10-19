package com.delicious.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.delicious.pojo.entity.auth.RelationRolePermission;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
public interface RelationRolePermissionsService extends IService<RelationRolePermission> {

    int DelByEntity(RelationRolePermission relationRolePermission);
}
