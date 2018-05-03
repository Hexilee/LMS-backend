package com.zjuqsc.library.advice;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Li Chenxi
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final String DATA_CONFLICT = "Data conflict";
    private static final String BODY_INVALID = "Request data invalid";

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorInfoDto constraintViolationHandler(HttpServletRequest req, DataIntegrityViolationException e) throws Exception {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        errorInfoDto.getErrors().add(e.getRootCause().getLocalizedMessage());
        return errorInfoDto;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfoDto methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException exception) throws Exception {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(BODY_INVALID);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorInfoDto.getErrors().add(error.getField());
        }
        return errorInfoDto;
    }
}
