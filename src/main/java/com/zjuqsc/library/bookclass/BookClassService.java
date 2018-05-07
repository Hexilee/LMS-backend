package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Li Chenxi
 */
public interface BookClassService {
    Page<BookClassDto> getBookClassList(Integer pageNum, Integer limit);
    BookClassDto getBookClass(Integer bcid);
    BookClassDto getBookClass(String isbn);
}
