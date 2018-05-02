package com.zjuqsc.library.user;

import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.repository.UserRepository;
import com.zjuqsc.library.user.dto.CreateUserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Li Chenxi
 */
@Component
public class UserService {
    @Value("${security.bcrypt.rounds}")
    int rounds;

    private UserRepository userRepository;
    public UserService (UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User newUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setName(createUserDto.getName());
        user.setPassword(new BCryptPasswordEncoder(rounds).encode(createUserDto.getPassword()));
        return user;
    }
}
