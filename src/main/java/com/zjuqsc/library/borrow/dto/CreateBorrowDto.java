package com.zjuqsc.library.borrow.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateBorrowDto {
    @NotNull
    Integer bid;

    @NotNull
    Integer seconds;
}
