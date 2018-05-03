package com.zjuqsc.library.auth;

import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.auth.dto.UserInfoDto;
import com.zjuqsc.library.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author Li Chenxi
 */
@Component
public class AuthFactory {
    @Value("${security.jwt.expiration}")
    private Integer expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    public TokenDto genToken(UserInfoDto userInfoDto) {
        String token = Jwts.builder()
                .claim(AuthConstant.USER_KEY, userInfoDto)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        return new TokenDto(expiration, token);
    }

    public UserInfoDto genUserInfo(User user) {
        UserInfoDto info = new UserInfoDto();
        info.setUid(user.getUid());
        info.setEmail(user.getEmail());
        info.setUsername(user.getUsername());
        info.setName(user.getName());
        info.setPassword("******");
        info.setAuthorities(
                new ArrayList<SimpleGrantedAuthority>() {{
                    add(new SimpleGrantedAuthority(AuthConstant.ROLE_USER));
                    if (user.isAdmin()) {
                        add(new SimpleGrantedAuthority(AuthConstant.ROLE_ADMIN));
                    }
                }}
        );
        return info;
    }
}
