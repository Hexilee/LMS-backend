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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

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

    @Value("${security.jwt.header}")
    private String tokenHeader;

    @Value("${security.jwt.tokenHead}")
    private String tokenHead;

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

    public Optional<UserInfoDto> createUserInfo(String token) {
        Optional<UserInfoDto> userInfoDto = Optional.empty();
        try {
            String sub = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            log.info("Sub: " + sub);
            userInfoDto = Optional.ofNullable(
                    jacksonObjectMapper.readValue(
                            sub,
                            UserInfoDto.class
                    )
            );
        } catch (IOException e) {
            log.info(e.getLocalizedMessage());
        }
        return userInfoDto;
    }

    public Optional<String> getToken(HttpServletRequest request) {
        Optional<String> token = Optional.empty();
        String authHeader = request.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            token = Optional.of(authHeader.substring(tokenHead.length()));
        }
        return token;
    }
}
