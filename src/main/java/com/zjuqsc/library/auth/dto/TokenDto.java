package com.zjuqsc.library.auth.dto;

import lombok.Getter;

/**
 * @author Li Chenxi
 */
public class TokenDto {
    @Getter
    private final int expiresIn;

    @Getter
    private final String token;

    public TokenDto(int expiresIn, String token) {
        this.expiresIn = expiresIn;
        this.token = token;
    }
}
