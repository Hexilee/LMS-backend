package com.zjuqsc.library.user.dto;

import com.zjuqsc.library.Constants;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Li Chenxi
 */
public class CreateUserDto {
    /**
     * username cannot contain @
     */
    @Getter
    @Pattern(regexp = Constants.Regex.CANNOT_BE_EMAIL)
    private String username;

    @Getter
    @Email
    private String email;

    @Getter
    @NotBlank
    private String name;

    @Getter
    @NotBlank
    private String password;
}
