package com.zjuqsc.library.user.dto;

import com.zjuqsc.library.Constants;
import lombok.Data;

import javax.validation.constraints.*;

/**
 * @author Li Chenxi
 */
@Data
public class CreateUserDto {
    /**
     * username cannot contain @
     */
    @Pattern(regexp = Constants.Regex.CANNOT_BE_EMAIL)
    @Size(max = 31)
    @NotNull
    private String username;

    @Email
    @Size(max = 31)
    @NotBlank
    private String email;

    @Size(max = 31)
    @NotBlank
    private String name;

    @NotBlank
    private String password;
}
