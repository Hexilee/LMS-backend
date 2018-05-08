package com.zjuqsc.library.repository;

import com.zjuqsc.library.entity.BookClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Li Chenxi
 */
public interface BookClassRepository extends JpaRepository<BookClass, Integer>, JpaSpecificationExecutor<BookClass> {
    BookClass findByIsbn(String isbn);
}
