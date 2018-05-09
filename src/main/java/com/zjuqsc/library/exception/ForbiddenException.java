package com.zjuqsc.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.HttpRetryException;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends HttpRetryException {
    public ForbiddenException() {
        super(HttpStatus.FORBIDDEN.getReasonPhrase(), HttpStatus.FORBIDDEN.value());
    }
}
