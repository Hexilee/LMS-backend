package com.zjuqsc.library.book;

import com.zjuqsc.library.book.dto.BookDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BookService {
    Optional<BookDto> register(Integer bcid);
    Optional<Page<BookDto>> getBooks(Integer bcid, Integer pages);
}
