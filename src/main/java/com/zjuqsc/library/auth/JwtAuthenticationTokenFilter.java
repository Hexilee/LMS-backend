package com.zjuqsc.library.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Li Chenxi
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private AuthUtils authUtils;

    @Autowired
    public JwtAuthenticationTokenFilter(AuthUtils authUtils) {
        this.authUtils = authUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authUtils.getToken(request).ifPresent(authToken -> {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                authUtils.createUserInfo(authToken)
                        .ifPresent(userDetails -> {
                            log.info("Authorized: " + userDetails.toString());
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                    request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
            }
        });
        filterChain.doFilter(request, response);
    }
}
