package com.zjuqsc.library.repository;

import com.zjuqsc.library.entity.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Li Chenxi
 */
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
    User findByEmail(String email);
}
