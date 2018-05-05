package com.zjuqsc.library.token;

import com.zjuqsc.library.Constants;
import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthService;
import com.zjuqsc.library.auth.dto.TokenDto;
import com.zjuqsc.library.token.dto.LoginDto;
import com.zjuqsc.library.token.exception.ResourceKeyNotExistException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.regex.Pattern;

/**
 * @author Li Chenxi
 */
@Api(value = "/token", description = "Controller for token resources")
@ApiResponses({
        @ApiResponse(code = 400, message = "Bad Request", response = ErrorInfoDto.class),
        @ApiResponse(code = 409, message = "Conflict", response = ErrorInfoDto.class),
})
@RestController
@RequestMapping("/token")
public class TokenController {
    private AuthService authService;

    @Autowired
    public TokenController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "Login, generate a token")
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(
            @Validated @RequestBody LoginDto loginDto
    ) throws ResourceKeyNotExistException {
        TokenDto tokenDto;
        if (Pattern.matches(Constants.Regex.CANNOT_BE_EMAIL, loginDto.getUsernameOrEmail())) {
            tokenDto = authService.loginWithUsername(loginDto.getUsernameOrEmail(), loginDto.getPassword());
            if (tokenDto == null) {
                throw new ResourceKeyNotExistException("Username", loginDto.getUsernameOrEmail());
            }
        } else {
            tokenDto = authService.loginWithEmail(loginDto.getUsernameOrEmail(), loginDto.getPassword());
            if (tokenDto == null) {
                throw new ResourceKeyNotExistException("Email", loginDto.getUsernameOrEmail());
            }
        }
        return tokenDto;
    }
}
