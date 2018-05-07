package com.zjuqsc.library.token.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Li Chenxi
 */
@Data
public class LoginDto {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
