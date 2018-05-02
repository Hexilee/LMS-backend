package com.zjuqsc.library.user;

import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.user.dto.CreateUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li Chenxi
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto register(
            @Validated @RequestBody CreateUserDto createUserDto
    ) {
        userService.saveUser(
                userService.newUser(createUserDto)
        );
        return new TokenDto(3600, "new token");
    }
}
