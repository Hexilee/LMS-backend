package com.zjuqsc.library.book.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class BookDto {
    @NotNull
    private Integer bid;

    @NotNull
    private Integer bcid;

    @NotNull
    private boolean accessible;

    @NotNull
    private Instant createdAt;
}
