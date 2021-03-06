package com.zjuqsc.library.repository;

import com.zjuqsc.library.entity.BookClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author Li Chenxi
 */
@Repository
public interface BookClassRepository extends JpaRepository<BookClass, Integer>, JpaSpecificationExecutor<BookClass> {
    BookClass findByIsbn(String isbn);
}
