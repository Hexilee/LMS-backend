package com.zjuqsc.library.bookclass.dto;

import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Builder
public class BookClassDto {
    @NotNull
    private Integer bcid;

    @ISBN
    @NotBlank
    private String isbn;

    @NotBlank
    private String name;

    @NotBlank
    private String author;

    @NotBlank
    private String description;

    @NotNull
    private Instant publishedAt;
}
