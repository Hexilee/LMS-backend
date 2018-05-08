package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.auth.AuthConstant;
import com.zjuqsc.library.bookclass.dto.BookClassDto;
import com.zjuqsc.library.bookclass.dto.BookClassQueryDto;
import com.zjuqsc.library.bookclass.dto.CreateBookClassDto;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @author Li Chenxi
 */
@Api(value = "/bookClass", description = "Controller for bookClass resources")
@RestController
@RequestMapping("/bookClass")
public class BookClassController {
    private static final String PAGE_ID_KEY = "pages";
    private static final String QUERY_KEY = "query";

    private BookClassService bookClassService;
    private BookClassUtils bookClassUtils;

    @Autowired
    public BookClassController(BookClassService bookClassService, BookClassUtils bookClassUtils) {
        this.bookClassService = bookClassService;
        this.bookClassUtils = bookClassUtils;
    }

    @ApiOperation(value = "Get bookClasses")
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Page<BookClassDto> getBookClasses(
            @RequestParam(value = PAGE_ID_KEY, defaultValue = "0") Integer pages,
            @RequestParam(value = QUERY_KEY, required = false) String query
    ) {
        if (query == null) {
            return bookClassService.getBookClassPage(pages);
        }
        return bookClassService.getBookClassPage(pages, new BookClassQueryDto(query));
    }

    @ApiOperation(value = "Create a new bookClass", authorizations = @Authorization(value = "authKey"))
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request", response = ErrorInfoDto.class),
            @ApiResponse(code = 409, message = "Conflict", response = ErrorInfoDto.class),
    })
    @RolesAllowed(AuthConstant.ADMIN)
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BookClassDto register(@Validated @RequestBody CreateBookClassDto createBookClassDto) {
        return bookClassService.register(bookClassUtils.create(createBookClassDto));
    }
}