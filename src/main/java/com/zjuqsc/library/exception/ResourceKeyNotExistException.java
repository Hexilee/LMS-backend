package com.zjuqsc.library.exception;

import org.springframework.http.HttpStatus;

import java.net.HttpRetryException;

public class ResourceKeyNotExistException extends HttpRetryException {
    public ResourceKeyNotExistException(String key, String value) {
        super(new StringBuilder(key).append(" ").append(value).append(" not exists.").toString(), HttpStatus.CONFLICT.value());
    }

    public ResourceKeyNotExistException(String key, Integer value) {
        super(new StringBuilder(key).append(" ").append(value).append(" not exists.").toString(), HttpStatus.CONFLICT.value());
    }
}