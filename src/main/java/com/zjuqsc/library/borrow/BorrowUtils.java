package com.zjuqsc.library.borrow;

import com.zjuqsc.library.borrow.dto.BorrowDto;
import com.zjuqsc.library.borrow.dto.CreateBorrowDto;
import com.zjuqsc.library.entity.Book;
import com.zjuqsc.library.entity.Borrow;
import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class BorrowUtils {
    private BookRepository bookRepository;

    @Autowired
    public BorrowUtils(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Optional<Borrow> createBorrow(CreateBorrowDto createBorrowDto, User user) {
        return bookRepository.findById(createBorrowDto.getBid())
                .filter(Book::isAccessible)
                .map(book -> {
                            Borrow borrow = new Borrow();
                            borrow.setBook(book);
                            borrow.setUser(user);
                            borrow.setShouldReturnAt(Instant.ofEpochSecond(System.currentTimeMillis() / 1000 + createBorrowDto.getSeconds()));
                            return borrow;
                        }
                );
    }

    public BorrowDto createBorrowDto(Borrow borrow) {
        return new BorrowDto() {{
            setBorrowId(borrow.getBorrowId());
            setBid(borrow.getBook().getBid());
            setUid(borrow.getUser().getUid());
            setCreatedAt(borrow.getCreatedAt());
            setShouldReturnAt(borrow.getShouldReturnAt());
            setReturnedAt(borrow.getReturnedAt());
        }};
    }
}
