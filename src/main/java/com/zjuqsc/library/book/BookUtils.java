package com.zjuqsc.library.book;

import com.zjuqsc.library.book.dto.BookDto;
import com.zjuqsc.library.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookUtils {
    public BookDto creaetBookDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setBid(book.getBid());
        bookDto.setBcid(book.getBookClass().getBcid());
        bookDto.setAccessible(book.isAccessible());
        bookDto.setCreatedAt(book.getCreatedAt());
        return bookDto;
    }
}
