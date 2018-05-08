package com.zjuqsc.library.advice;

import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import com.zjuqsc.library.exception.ObjectNotFoundException;
import com.zjuqsc.library.token.exception.ResourceKeyNotExistException;
import io.jsonwebtoken.JwtException;
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
    public ErrorInfoDto constraintViolationHandler(HttpServletRequest req, DataIntegrityViolationException e) {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        errorInfoDto.getErrors().add(e.getRootCause().getLocalizedMessage());
        return errorInfoDto;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorInfoDto methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException exception) {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(BODY_INVALID);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errorInfoDto.getErrors().add(error.getField());
        }
        return errorInfoDto;
    }

    @ExceptionHandler(value = ResourceKeyNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorInfoDto httpRetryExceptionHandler(HttpServletRequest req, ResourceKeyNotExistException e) {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(DATA_CONFLICT);
        errorInfoDto.setUrl(req.getRequestURL().toString());
        errorInfoDto.getErrors().add(e.getMessage());
        return errorInfoDto;
    }

    @ExceptionHandler(value = JwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorInfoDto expiredJwtExceptionHandler(HttpServletRequest req, JwtException e) {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
        errorInfoDto.setUrl(req.getRequestURL().toString());
        errorInfoDto.getErrors().add(e.getLocalizedMessage());
        return errorInfoDto;
    }

    @ExceptionHandler(value = ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorInfoDto objectNotFoundExceptionHandler(HttpServletRequest req, ObjectNotFoundException e) {
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
        errorInfoDto.setUrl(req.getRequestURL().toString());
        errorInfoDto.getErrors().add(e.getLocalizedMessage());
        return errorInfoDto;
    }
}
