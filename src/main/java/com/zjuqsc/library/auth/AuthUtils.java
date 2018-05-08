package com.zjuqsc.library.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.auth.dto.UserInfoDto;
import com.zjuqsc.library.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author Li Chenxi
 */
@Slf4j
@Component
public class AuthUtils {
    @Value("${security.jwt.expiration}")
    private Integer expiration;

    @Value("${security.jwt.secret}")
    private String secret;

    private ObjectMapper jacksonObjectMapper;

    @Autowired
    public AuthUtils(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    public TokenDto createTokenDto(UserInfoDto userInfoDto) {
        String token = "";
        try {
            token = Jwts.builder()
                    .setSubject(jacksonObjectMapper.writeValueAsString(userInfoDto))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            System.exit(1);
        }
        return new TokenDto(expiration, token);

    }

    public UserInfoDto createUserInfo(User user) {
        UserInfoDto info = new UserInfoDto();
        info.setUid(user.getUid());
        info.setEmail(user.getEmail());
        info.setUsername(user.getUsername());
        info.setName(user.getName());
        info.setPassword("******");
        info.setAdmin(user.isAdmin());
        return info;
    }

    public UserInfoDto createUserInfo(String token) {
        String sub = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        try {
            log.info("Sub: " + sub);
            return jacksonObjectMapper.readValue(
                    sub,
                    UserInfoDto.class
            );
        } catch (IOException e) {
            log.info(e.getLocalizedMessage());
            return null;
        }
    }
}
