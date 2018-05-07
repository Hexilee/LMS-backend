package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import com.zjuqsc.library.bookclass.dto.CreateBookClassDto;
import com.zjuqsc.library.entity.BookClass;
import org.springframework.stereotype.Component;

@Component
public class BookClassUtils {

    public BookClassDto create(BookClass bookClass) {
        BookClassDto bookClassDto = new BookClassDto();
        bookClassDto.setBcid(bookClass.getBcid());
        bookClassDto.setIsbn(bookClass.getIsbn());
        bookClassDto.setName(bookClass.getName());
        bookClassDto.setDescription(bookClass.getDescription());
        bookClassDto.setAuthor(bookClass.getAuthor());
        bookClassDto.setPublishedAt(bookClass.getPublishedAt());
        return bookClassDto;
    }

    public BookClass create(CreateBookClassDto createBookClassDto) {
        BookClass bookClass = new BookClass();
        bookClass.setIsbn(createBookClassDto.getIsbn());
        bookClass.setName(createBookClassDto.getName());
        bookClass.setDescription(createBookClassDto.getDescription());
        bookClass.setAuthor(createBookClassDto.getAuthor());
        bookClass.setPublishedAt(createBookClassDto.getPublishedAt());
        return bookClass;
    }
}
