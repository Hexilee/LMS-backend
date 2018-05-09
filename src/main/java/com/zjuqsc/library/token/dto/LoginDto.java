package com.zjuqsc.library.token.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Li Chenxi
 */
@Data
public class LoginDto {
    @ApiModelProperty(example = "Hex")
    @NotBlank
    @Size(max = 31)
    private String usernameOrEmail;

    @ApiModelProperty(example = "123456")
    @NotBlank
    private String password;
}
