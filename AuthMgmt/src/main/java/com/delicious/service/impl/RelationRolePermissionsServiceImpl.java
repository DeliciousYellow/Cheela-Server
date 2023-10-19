package com.delicious.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.mapper.RelationRolePermissionsMapper;
import com.delicious.pojo.entity.auth.RelationRolePermission;
import com.delicious.service.RelationRolePermissionsService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@Service
public class RelationRolePermissionsServiceImpl extends ServiceImpl<RelationRolePermissionsMapper, RelationRolePermission> implements RelationRolePermissionsService {
    @Resource
    private RelationRolePermissionsMapper relationRolePermissionsMapper;
    @Override
    public int DelByEntity(RelationRolePermission relationRolePermission) {
        return relationRolePermissionsMapper.DelByEntity(relationRolePermission);
    }
}
