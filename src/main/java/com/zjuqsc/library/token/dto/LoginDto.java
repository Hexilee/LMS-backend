package com.zjuqsc.library.token.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Li Chenxi
 */
@Data
public class LoginDto {
    @NotBlank
    @Size(max = 31)
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
