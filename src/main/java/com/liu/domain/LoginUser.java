package com.liu.domain;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liu
 * @create 2023-10-12-20:25
 */
@Data
@NoArgsConstructor
//实现UserDetails作为UserDetailsService的实现类的返回
//LoginUser和User分开实现不同职能，实现解耦
public class LoginUser implements UserDetails {

    private User user;
    //存储权限信息
    private List<String> permissions;

    public LoginUser(User user,List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }
    //存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false) //不进行序列化
    private List<GrantedAuthority> authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        authorities= permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;
        //        List<GrantedAuthority> authorities= new ArrayList<>();
//        for(String permission:permissions){
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//            authorities.add(authority)
//        }
//        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
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
