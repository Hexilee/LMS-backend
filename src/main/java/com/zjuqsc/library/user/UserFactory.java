package com.zjuqsc.library.user;

import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.user.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Li Chenxi
 */
@Component
public class UserFactory {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User create(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        return user;
    }
}
