package com.zjuqsc.library.bookclass;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.bookclass.dto.BookClassDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Li Chenxi
 */
@Api(value = "/bookClass", description = "Controller for bookClass resources")
@RestController
@RequestMapping("/bookClass")
public class BookClassController {
//    @ApiOperation(value = "Get bookClasses")
//    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    @ResponseStatus(HttpStatus.OK)
//    List<BookClassDto> getBookClasses(){
//
//    }
}
