package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author Li Chenxi
 */
@Service
public class BookClassServiceImpl implements BookClassService {

    @Override
    public Page<BookClassDto> getBookClassList(Integer pageNum, Integer limit) {
        return null;
    }

    @Override
    public BookClassDto getBookClass(Integer bcid) {
        return null;
    }

    @Override
    public BookClassDto getBookClass(String isbn) {
        return null;
    }
}
