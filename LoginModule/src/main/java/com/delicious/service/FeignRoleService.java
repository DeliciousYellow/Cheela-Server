package com.delicious.service;

import com.delicious.pojo.Result;
import com.delicious.pojo.entity.Permission;
import com.delicious.pojo.entity.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "AuthMgmt",path = "/role")
public interface FeignRoleService {
    @GetMapping("/QueryPermissionsByUserID/{id}")
    Result<List<Permission>> QueryPermissionsByUserID(@PathVariable Integer id);

    @GetMapping("/QueryRolesByUserID/{id}")
    Result<List<Role>> QueryRolesByUserID(@PathVariable Integer id);
}
