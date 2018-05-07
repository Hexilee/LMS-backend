package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import com.zjuqsc.library.bookclass.dto.BookClassQueryDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Li Chenxi
 */
public interface BookClassService {
    Page<BookClassDto> getBookClassPage(Integer pageNum);
    Page<BookClassDto> getBookClassPage(Integer pageNum, BookClassQueryDto bookClassQueryDto);
    BookClassDto getBookClass(Integer bcid);
    BookClassDto getBookClass(String isbn);
}
