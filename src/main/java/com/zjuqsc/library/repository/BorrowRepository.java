package com.zjuqsc.library.repository;

import com.zjuqsc.library.entity.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BorrowRepository extends JpaRepository<Borrow, Integer>, JpaSpecificationExecutor<Borrow> {
}
