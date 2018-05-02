package com.zjuqsc.library.advice;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;

/**
 * @author Li Chenxi
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String DATA_CONFLICT = "Data conflict";

    @ExceptionHandler(value = TransactionSystemException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorInfoDto constraintViolationHandler(HttpServletRequest req, TransactionSystemException e) throws Exception {
        ErrorInfoDto errorInfoDto = new ErrorInfoDto<String>();
        errorInfoDto.setTime(Instant.now());
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        List<String> error = errorInfoDto.getErrors();
        error.add(e.getApplicationException().toString());
        return errorInfoDto;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorInfoDto exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        ErrorInfoDto errorInfoDto = new ErrorInfoDto<String>();
        errorInfoDto.setTime(Instant.now());
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        List<String> error = errorInfoDto.getErrors();
        error.add(e.getClass().getName());
        return errorInfoDto;
    }
}
