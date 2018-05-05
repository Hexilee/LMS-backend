package com.zjuqsc.library.token.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * @author Li Chenxi
 */
public class LoginDto {
    @Getter
    @NotBlank
    private String usernameOrEmail;

    @Getter
    @NotBlank
    private String password;
}
