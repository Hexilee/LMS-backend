package com.zjuqsc.library.auth;

import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.entity.User;

/**
 * @author Li Chenxi
 */
public interface AuthService {
    /**
     *
     * @param user User to be created
     * @return tokenDto token and expiration time
     */
    TokenDto register(User user);
    String login(String username, String password);
}
