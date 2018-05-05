package com.zjuqsc.library.repository;

import com.zjuqsc.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Li Chenxi
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
