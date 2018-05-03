package com.zjuqsc.library.auth;


import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Li Chenxi
 */
@Service
public class AuthServiceImpl implements AuthService {
    private AuthFactory authFactory;
    private UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(AuthFactory authFactory, UserRepository userRepository) {
        this.authFactory = authFactory;
        this.userRepository = userRepository;
    }

    @Override
    public TokenDto register(User user) {
        return authFactory.genToken(
                authFactory.genUserInfo(
                        userRepository.save(user)
                )
        );
    }

    @Override
    public String login(String username, String password) {
        return null;
    }
}
