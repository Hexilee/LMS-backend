package com.zjuqsc.library.borrow;

import com.zjuqsc.library.borrow.dto.BorrowDto;
import com.zjuqsc.library.entity.Borrow;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BorrowService {
    BorrowDto register(Borrow borrow);

    Optional<BorrowDto> returnByBorrowId(Integer borrowId, Integer uid);

    Optional<Page<BorrowDto>> getRecords(Integer uid, Integer pages);

    Optional<Page<BorrowDto>> getRecords(Integer uid, Integer pages, BorrowStatus status);
}