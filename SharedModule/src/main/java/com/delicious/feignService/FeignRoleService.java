package com.delicious.feignService;

import com.delicious.pojo.Result;
import com.delicious.pojo.entity.auth.Permission;
import com.delicious.pojo.entity.auth.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(value = "AuthMgmt",path = "/role")
public interface FeignRoleService {
    @GetMapping("/QueryPermissionsByUserID/{id}")
    Result<List<Permission>> QueryPermissionsByUserID(@PathVariable Integer id,@RequestHeader("X-Token") String token);

    @GetMapping("/QueryRolesByUserID/{id}")
    Result<List<Role>> QueryRolesByUserID(@PathVariable Integer id,@RequestHeader("X-Token") String token);
}
