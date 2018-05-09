package com.zjuqsc.library.borrow;

import com.zjuqsc.library.auth.AuthService;
import com.zjuqsc.library.borrow.dto.BorrowDto;
import com.zjuqsc.library.entity.Borrow;
import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.repository.BorrowRepository;
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowServiceImpl implements BorrowService {
    private static final String BORROW_KEY = "borrowId";

    @Value("${pageable.size}")
    private Integer size;

    private BorrowRepository borrowRepository;
    private BorrowUtils borrowUtils;
    private AuthService authService;

    @Autowired
    public BorrowServiceImpl(BorrowRepository borrowRepository, BorrowUtils borrowUtils, AuthService authService) {
        this.borrowRepository = borrowRepository;
        this.borrowUtils = borrowUtils;
        this.authService = authService;
    }

    @Override
    public BorrowDto register(Borrow borrow) {
        return borrowUtils.createBorrowDto(borrowRepository.saveAndFlush(borrow));
    }

    @Override
    public Optional<BorrowDto> returnByBorrowId(Integer borrowId, Integer uid) {
        return borrowRepository.findById(borrowId)
                .filter(borrow -> uid.equals(borrow.getUser().getUid()))
                .map(borrow -> {
                            borrow.setReturnedAt(Instant.now());
                            return borrow;
                        }
                )
                .map(borrow -> borrowUtils.createBorrowDto(borrow));
    }

    @Override
    public Optional<Page<BorrowDto>> getRecords(Integer uid, Integer pages) {
        return authService.getUser(uid)
                .map(user -> {
                    Pageable pageable = new PageRequest(pages, size, Sort.Direction.DESC, BORROW_KEY);
                    return borrowRepository.findAll(
                            (Root<Borrow> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
                                List<Predicate> list = new ArrayList<>();
                                list.add(criteriaBuilder.equal(root.get("user").as(User.class), user));
                                Predicate[] p = new Predicate[list.size()];
                                return criteriaBuilder.and(list.toArray(p));
                            }, pageable);
                })
                .map(borrows -> borrows
                        .map(borrow -> borrowUtils.createBorrowDto(borrow))
                );
    }
}
