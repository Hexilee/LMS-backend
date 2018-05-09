package com.zjuqsc.library.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zjuqsc.library.advice.dto.ErrorInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Component
public class RestAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final String AUTH_FAIL = "Fail in Authentication";

    private ObjectMapper objectMapper;

    @Autowired
    public RestAuthenticationFailureHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ErrorInfoDto<String> errorInfoDto = new ErrorInfoDto<>();
        errorInfoDto.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
        errorInfoDto.setUrl(request.getRequestURL().toString());
        errorInfoDto.getErrors().add(AUTH_FAIL);

        OutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, errorInfoDto);
        outputStream.flush();
    }
}
