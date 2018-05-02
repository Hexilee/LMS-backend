package com.zjuqsc.library.advice;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;

/**
 * @author Li Chenxi
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final String DATA_CONFLICT = "Data conflict";

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ErrorInfoDto constraintViolationHandler(HttpServletRequest req, DataIntegrityViolationException e) throws Exception {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        errorInfoDto.getError().add(e.getRootCause().getLocalizedMessage());
        return errorInfoDto;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ValidationException.class)
    public ErrorInfoDto ExceptionHandler(HttpServletRequest req, ValidationException e) throws Exception {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        for (Throwable err : e.getSuppressed()) {
            errorInfoDto.getError().add(err.getMessage());
        }
        return errorInfoDto;
    }
}
