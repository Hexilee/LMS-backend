package com.zjuqsc.library.exception;

import org.springframework.http.HttpStatus;

import java.net.HttpRetryException;

public class ObjectNotFoundException extends HttpRetryException {
    public ObjectNotFoundException(String resource, Integer id) {
        super(resource + " " + id + " not exist", HttpStatus.NOT_FOUND.value());
    }
}
