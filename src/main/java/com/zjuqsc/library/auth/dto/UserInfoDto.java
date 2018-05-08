package com.zjuqsc.library.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zjuqsc.library.auth.AuthConstant;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Li Chenxi
 */
@Data
public class UserInfoDto implements UserDetails {
    private int uid;
    private String username;
    private String email;
    private String password;
    private String name;
    private boolean isAdmin;

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<SimpleGrantedAuthority>() {{
            add(new SimpleGrantedAuthority(AuthConstant.ROLE_USER));
            if (isAdmin) {
                add(new SimpleGrantedAuthority(AuthConstant.ROLE_ADMIN));
            }
        }};
    }
}