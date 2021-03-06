package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import com.zjuqsc.library.bookclass.dto.BookClassQueryDto;
import com.zjuqsc.library.entity.BookClass;
import org.springframework.data.domain.Page;

/**
 * @author Li Chenxi
 */
public interface BookClassService {
    Page<BookClassDto> getBookClassPage(Integer pageNum);
    Page<BookClassDto> getBookClassPage(Integer pageNum, BookClassQueryDto bookClassQueryDto);
    BookClassDto getBookClass(Integer bcid);
    BookClassDto getBookClass(String isbn);
    BookClassDto register(BookClass bookClass);
}