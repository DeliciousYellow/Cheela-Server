package com.delicious.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.delicious.mapper.RolesMapper;
import com.delicious.pojo.entity.auth.Permission;
import com.delicious.pojo.entity.auth.Role;
import com.delicious.service.RolesService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 黄灿
 * @since 2023-09-07
 */
@Service
public class RolesServiceImpl extends ServiceImpl<RolesMapper, Role> implements RolesService {

    @Resource
    private RolesMapper rolesMapper;

    @Override
    public List<Permission> QueryPermissionsByUserID(Integer id) {
        return rolesMapper.QueryPermissionsByUserID(id);
    }

    @Override
    public List<Permission> QueryPermissionsByRoleID(Integer id) {
        return rolesMapper.QueryPermissionsByRoleID(id);
    }

    @Override
    public List<Role> QueryRolesByUserID(Integer id) {
        return rolesMapper.QueryRolesByUserID(id);
    }
}
