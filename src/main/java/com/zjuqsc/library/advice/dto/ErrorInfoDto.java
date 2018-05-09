package com.zjuqsc.library.advice.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Li Chenxi
 */
@ApiModel
@Data
public class ErrorInfoDto<T> {
    private Instant time = Instant.now();
    private String message;
    private String url;

    @ApiModelProperty(example = "[email]")
    private List<T> errors = new ArrayList<>();
}
