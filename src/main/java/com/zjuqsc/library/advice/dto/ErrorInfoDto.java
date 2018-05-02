package com.zjuqsc.library.advice.dto;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Chenxi
 */
@Data
public class ErrorInfoDto<T> {
    private Instant time;
    private String message;
    private String url;
    private List<T> errors = new ArrayList<>();
}
