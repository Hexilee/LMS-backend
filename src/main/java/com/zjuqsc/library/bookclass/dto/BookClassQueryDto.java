package com.zjuqsc.library.bookclass.dto;

import lombok.Data;

@Data
public class BookClassQueryDto {
    private String isbn;
    private String name;
    private String author;

    public BookClassQueryDto(String query) {
        this.isbn = query;
        this.name = query;
        this.author = query;
    }
}
