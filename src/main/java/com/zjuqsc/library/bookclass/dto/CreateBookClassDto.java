package com.zjuqsc.library.bookclass.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.Instant;

/**
 * @author Li Chenxi
 */
@Data
public class CreateBookClassDto {
    @ISBN
    @NotBlank
    @Size(max = 31)
    @ApiModelProperty(example = "978-7-111-35305-8")
    private String isbn;

    @NotBlank
    @Size(max = 127)
    @ApiModelProperty(example = "Computer Organization and Design: Hardware and software interfaces (4) [Paperback](Chinese Edition)")
    private String name;

    @NotBlank
    @Size(max = 63)
    @ApiModelProperty(example = "John L Hennessey")
    private String author;

    @NotBlank
    @Size(max = 255)
    @ApiModelProperty
    private String description;

    @NotNull
    @Past
    @ApiModelProperty(example = "2012-01-11T00:00:00.000Z")
    private Instant publishedAt;
}
