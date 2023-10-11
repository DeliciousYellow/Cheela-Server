package com.delicious.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.delicious.pojo.entity.Permission;
import com.delicious.pojo.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
//@AllArgsConstructor
public class LoginUserDetails implements UserDetails {

    private User user;

    private List<Permission> permissions;

    @JSONField(serialize = false)
    private volatile List<SimpleGrantedAuthority> simpleGrantedAuthorityList;

    @Builder
    public LoginUserDetails(User user, List<Permission> permissions){
        this.user = user;
        this.permissions = permissions;
    }

    @Override
    @JSONField(serialize = false)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (simpleGrantedAuthorityList == null){
            synchronized (this){
                if (simpleGrantedAuthorityList == null){
                    this.simpleGrantedAuthorityList = permissions.stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                            .collect(Collectors.toList());
                }
            }
        }
        //把permissions中的权限信息封装成 SimpleGrantedAuthority 类型的对象
        return this.simpleGrantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
