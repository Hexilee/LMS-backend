package com.zjuqsc.library.book;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthConstant;
import com.zjuqsc.library.book.dto.BookDto;
import com.zjuqsc.library.exception.ResourceKeyNotExistException;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author Li Chenxi
 */
@Slf4j
@Api(value = "/book", description = "Controller for book resources")
@RestController
@RequestMapping("/book")
public class BookController {
    private static final String BCID_KEY = "bcid";
    private static final String PAGE_ID_KEY = "pages";
    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @ApiOperation(value = "Get book list by bcid")
    @ApiResponses({
            @ApiResponse(code = 409, message = "Conflict", response = ErrorInfoDto.class),
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<BookDto> getBooks(
            @RequestParam(value = BCID_KEY) Integer bcid,
            @RequestParam(value = PAGE_ID_KEY, defaultValue = "0") Integer pages
    ) throws ResourceKeyNotExistException {
        return bookService.getBooks(bcid, pages)
                .orElseThrow(() -> new ResourceKeyNotExistException(BCID_KEY, bcid));
    }

    @ApiOperation(value = "Register a book by bcid", authorizations = @Authorization(value = AuthConstant.JWT_AUTH_NAME))
    @ApiResponses({
            @ApiResponse(code = 409, message = "Conflict", response = ErrorInfoDto.class),
            @ApiResponse(code = 403, message = "Forbidden"),
    })
    @PreAuthorize("hasRole(T(com.zjuqsc.library.auth.AuthConstant).ADMIN)")
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto register(
            @RequestParam(value = BCID_KEY) Integer bcid
    ) throws ResourceKeyNotExistException {
        return bookService.register(bcid)
                .orElseThrow(() -> new ResourceKeyNotExistException(BCID_KEY, bcid));
    }
}
