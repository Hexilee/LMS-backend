package com.zjuqsc.library.user;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthService;
import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.user.dto.CreateUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li Chenxi
 */
@Api(value = "/user", description = "Controller for user resources")
@ApiResponses({
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorInfoDto.class),
        @ApiResponse(code = 409, message = "Conflict", response = ErrorInfoDto.class),
})
@RestController
@RequestMapping("/user")
public class UserController {
    private AuthService authService;
    private UserFactory userFactory;

    @Autowired
    public UserController(AuthService authService, UserFactory userFactory) {
        this.authService = authService;
        this.userFactory = userFactory;
    }

    @ApiOperation(value = "Create new user")
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TokenDto register(
            @Validated @RequestBody CreateUserDto createUserDto
    ) {
        return authService.register(userFactory.create(createUserDto));
    }
}