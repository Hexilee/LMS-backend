package com.zjuqsc.library.user.dto;

import com.zjuqsc.library.Constants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Li Chenxi
 */
@Data
public class CreateUserDto {
    /**
     * username cannot contain @
     */
    @Pattern(regexp = Constants.Regex.CANNOT_BE_EMAIL)
    @NotNull
    private String username;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
