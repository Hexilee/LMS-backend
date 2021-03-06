package com.zjuqsc.library.book;

import com.zjuqsc.library.book.dto.BookDto;
import com.zjuqsc.library.entity.Book;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class BookUtils {
    public BookDto createBookDto(Book book) {
        val bookDto = new BookDto();
        bookDto.setBid(book.getBid());
        bookDto.setBcid(book.getBookClass().getBcid());
        bookDto.setAccessible(book.isAccessible());
        bookDto.setCreatedAt(book.getCreatedAt());
        return bookDto;
    }
}
