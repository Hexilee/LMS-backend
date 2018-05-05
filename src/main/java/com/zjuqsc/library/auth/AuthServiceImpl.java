package com.zjuqsc.library.auth;


import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Li Chenxi
 */
@Service
public class AuthServiceImpl implements AuthService {
    private AuthFactory authFactory;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(AuthFactory authFactory, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authFactory = authFactory;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private TokenDto genTokenDtoByUser(User user) {
        return authFactory.createTokenDto(
                authFactory.createUserInfo(user)
        );
    }

    @Override
    public TokenDto register(User user) {
        return genTokenDtoByUser(userRepository.saveAndFlush(user));
    }

    @Override
    public TokenDto loginWithEmail(String email, String password) {
        TokenDto tokenDto = null;
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            tokenDto = genTokenDtoByUser(user);
        }
        return tokenDto;
    }

    @Override
    public TokenDto loginWithUsername(String username, String password) {
        TokenDto tokenDto = null;
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            tokenDto = genTokenDtoByUser(user);
        }
        return tokenDto;
    }
}
