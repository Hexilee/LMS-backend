package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.bookclass.dto.BookClassDto;
import com.zjuqsc.library.bookclass.dto.BookClassQueryDto;
import com.zjuqsc.library.entity.BookClass;
import com.zjuqsc.library.repository.BookClassRepository;
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

/**
 * @author Li Chenxi
 */
@Service
public class BookClassServiceImpl implements BookClassService {
    private static final String BOOK_CLASS_ID_KEY = "bcid";

    @Value("${pageable.size}")
    private Integer size;

    private BookClassRepository bookClassRepository;
    private BookClassFactory bookClassFactory;

    @Autowired
    public BookClassServiceImpl(BookClassRepository bookClassRepository, BookClassFactory bookClassFactory) {
        this.bookClassRepository = bookClassRepository;
        this.bookClassFactory = bookClassFactory;
    }

    @Override
    public Page<BookClassDto> getBookClassPage(Integer pageNum) {
        Pageable pageable = new PageRequest(pageNum, size, Sort.Direction.DESC, BOOK_CLASS_ID_KEY);
        return bookClassRepository.findAll(pageable)
                .map(bookClass -> bookClassFactory.create(bookClass));
    }

    @Override
    public Page<BookClassDto> getBookClassPage(Integer pageNum, BookClassQueryDto bookClassQueryDto) {
        Pageable pageable = new PageRequest(pageNum, size, Sort.Direction.DESC, BOOK_CLASS_ID_KEY);
        Page<BookClass> bookClassPage = bookClassRepository.findAll(
                (Root<BookClass> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                    List<Predicate> list = new ArrayList<>();
                    if (null != bookClassQueryDto.getName() && !"".equals(bookClassQueryDto.getName())) {
                        list.add(criteriaBuilder.like(root.get("name").as(String.class), bookClassQueryDto.getName()));
                    }
                    if (null != bookClassQueryDto.getIsbn() && !"".equals(bookClassQueryDto.getIsbn())) {
                        list.add(criteriaBuilder.like(root.get("isbn").as(String.class), bookClassQueryDto.getIsbn()));
                    }
                    if (null != bookClassQueryDto.getAuthor() && !"".equals(bookClassQueryDto.getAuthor())) {
                        list.add(criteriaBuilder.like(root.get("author").as(String.class), bookClassQueryDto.getAuthor()));
                    }
                    Predicate[] p = new Predicate[list.size()];
                    return criteriaBuilder.and(list.toArray(p));

                }, pageable);
        return bookClassPage.map(bookClass -> bookClassFactory.create(bookClass));
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
