package com.zjuqsc.library.user.dto;

import com.zjuqsc.library.Constants;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "Hex")
    @Pattern(regexp = Constants.Regex.CANNOT_BE_EMAIL)
    @Size(max = 31)
    @NotNull
    private String username;

    @ApiModelProperty(example = "hexileee@gmail.com")
    @Email
    @Size(max = 31)
    @NotBlank
    private String email;

    @ApiModelProperty(example = "Li Chenxi")
    @Size(max = 31)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "123456")
    @NotBlank
    private String password;
}
