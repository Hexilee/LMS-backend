package com.zjuqsc.library.borrow;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthConstant;
import com.zjuqsc.library.auth.AuthService;
import com.zjuqsc.library.auth.AuthUtils;
import com.zjuqsc.library.auth.dto.UserInfoDto;
import com.zjuqsc.library.borrow.dto.BorrowDto;
import com.zjuqsc.library.borrow.dto.CreateBorrowDto;
import com.zjuqsc.library.entity.User;
import com.zjuqsc.library.exception.ForbiddenException;
import com.zjuqsc.library.exception.ObjectNotFoundException;
import com.zjuqsc.library.exception.ResourceKeyNotExistException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Li Chenxi
 */
@Api(value = "/record", description = "Controller for borrow record resources")
@RestController
@RequestMapping("/record")
public class BorrowController {
    private static final String PAGE_ID_KEY = "pages";
    private static final String STATUS_KEY = "status";

    private BorrowService borrowService;
    private BorrowUtils borrowUtils;
    private AuthUtils authUtils;
    private AuthService authService;

    @Autowired
    public BorrowController(BorrowService borrowService, BorrowUtils borrowUtils, AuthUtils authUtils, AuthService authService) {
        this.borrowService = borrowService;
        this.borrowUtils = borrowUtils;
        this.authUtils = authUtils;
        this.authService = authService;
    }

    @ApiOperation(value = "get record list", authorizations = @Authorization(value = AuthConstant.JWT_AUTH_NAME))
    @ApiResponses({
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PreAuthorize("hasRole(T(com.zjuqsc.library.auth.AuthConstant).USER)")
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<BorrowDto> getRecords(
            HttpServletRequest request,
            @RequestParam(value = PAGE_ID_KEY, defaultValue = "0") Integer pages,
            @RequestParam(value = STATUS_KEY, required = false) BorrowStatus status

    ) throws ForbiddenException {
        return authUtils.getToken(request)
                .map(token -> authUtils.createUserInfo(token).get())
                .map(userInfoDto -> {
                    if (status == null) {
                        return borrowService.getRecords(userInfoDto.getUid(), pages).get();
                    } else {
                        return borrowService.getRecords(userInfoDto.getUid(), pages, status).get();
                    }
                })
                .orElseThrow(ForbiddenException::new);
    }

    @ApiOperation(value = "new borrow record", authorizations = @Authorization(value = AuthConstant.JWT_AUTH_NAME))
    @ApiResponses({
            @ApiResponse(code = 409, message = "Conflict", response = ErrorInfoDto.class),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PreAuthorize("hasRole(T(com.zjuqsc.library.auth.AuthConstant).USER)")
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowDto borrow(
            HttpServletRequest request,
            @Validated @RequestBody CreateBorrowDto createBorrowDto
    ) throws ForbiddenException, ResourceKeyNotExistException {
        User user = authUtils.getToken(request)
                .map(token -> authUtils.createUserInfo(token).get())
                .map(userInfoDto -> authService.getUser(userInfoDto.getUid()).get())
                .orElseThrow(ForbiddenException::new);
        return borrowUtils.createBorrow(createBorrowDto, user)
                .map(borrow -> borrowService.register(borrow))
                .orElseThrow(() -> new ResourceKeyNotExistException("bid", createBorrowDto.getBid()));
    }

    @ApiOperation(value = "return book", authorizations = @Authorization(value = AuthConstant.JWT_AUTH_NAME))
    @ApiResponses({
            @ApiResponse(code = 404, message = "Not Found", response = ErrorInfoDto.class),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PreAuthorize("hasRole(T(com.zjuqsc.library.auth.AuthConstant).USER)")
    @PutMapping(path = "/{borrowId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BorrowDto returnBook(
            HttpServletRequest request,
            @PathVariable(value = "borrowId") Integer borrowId
    ) throws ObjectNotFoundException, ForbiddenException {
        UserInfoDto userInfoDto = authUtils.getToken(request)
                .map(token -> authUtils.createUserInfo(token).get())
                .orElseThrow(ForbiddenException::new);
        return borrowService.returnByBorrowId(borrowId, userInfoDto.getUid())
                .orElseThrow(() -> new ObjectNotFoundException("borrowId", borrowId));
    }
}
