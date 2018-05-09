package com.zjuqsc.library.borrow.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class BorrowDto {
    private Integer borrowId;
    private Integer bid;
    private Integer uid;
    private Instant createdAt;
    private Instant shouldReturnAt;
    private Instant returnedAt;
}
