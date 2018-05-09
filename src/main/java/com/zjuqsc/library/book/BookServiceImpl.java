package com.zjuqsc.library.book;

import com.zjuqsc.library.book.dto.BookDto;
import com.zjuqsc.library.entity.Book;
import com.zjuqsc.library.entity.BookClass;
import com.zjuqsc.library.repository.BookClassRepository;
import com.zjuqsc.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private static final String BOOK_ID_KEY = "bid";

    @Value("${pageable.size}")
    private Integer size;

    private BookRepository bookRepository;
    private BookClassRepository bookClassRepository;
    private BookUtils bookUtils;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookClassRepository bookClassRepository, BookUtils bookUtils) {
        this.bookRepository = bookRepository;
        this.bookClassRepository = bookClassRepository;
        this.bookUtils = bookUtils;
    }

    @Override
    public Optional<BookDto> register(Integer bcid) {
        return bookClassRepository.findById(bcid)
                .map(bookClass -> {
                    Book book = new Book();
                    book.setBookClass(bookClass);
                    book.setAccessible(true);
                    return book;
                })
                .map(book -> bookUtils.createBookDto(bookRepository.saveAndFlush(book)));
    }

    @Override
    public Optional<Page<BookDto>> getBooks(Integer bcid, Integer pages) {
        Optional<Page<BookDto>> bookDtoPage = Optional.empty();
        Optional<BookClass> bookClass = bookClassRepository.findById(bcid);
        if (bookClass.isPresent()) {
            Pageable pageable = new PageRequest(pages, size, Sort.Direction.DESC, BOOK_ID_KEY);
            Page<Book> bookPage = bookRepository.findAll(
                    (Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                        List<Predicate> list = new ArrayList<>();
                        list.add(criteriaBuilder.equal(root.get("bookClass").as(BookClass.class), bookClass.get()));
                        Predicate[] p = new Predicate[list.size()];
                        return criteriaBuilder.and(list.toArray(p));
                    }, pageable);
            bookDtoPage = Optional.of(bookPage.map(book -> bookUtils.createBookDto(book)));
        }
        return bookDtoPage;
    }
}
