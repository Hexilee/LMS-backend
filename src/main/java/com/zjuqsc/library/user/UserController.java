package com.zjuqsc.library.user;

import com.zjuqsc.library.auth.dto.TokenDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Li Chenxi
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TokenDto register() {
        return new TokenDto(3600, "new token");
    }
}
