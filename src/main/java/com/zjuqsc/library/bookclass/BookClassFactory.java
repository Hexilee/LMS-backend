package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import com.zjuqsc.library.entity.BookClass;
import org.springframework.stereotype.Component;

@Component
public class BookClassFactory {

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
}
