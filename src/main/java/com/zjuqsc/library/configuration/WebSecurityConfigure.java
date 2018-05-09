package com.zjuqsc.library.configuration;

import com.zjuqsc.library.auth.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Li Chenxi
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {
    @Value("${security.bcrypt.rounds}")
    int rounds;

    private static final String[] SWAGGER_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    public WebSecurityConfigure(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter, AuthenticationFailureHandler authenticationFailureHandler) {
        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(rounds);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers(
                        HttpMethod.POST,
                        "/user/**",
                        "/token/**"
                ).permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/bookClass/**",
                        "/book/**"
                ).permitAll()
                .anyRequest()
                .authenticated();
        httpSecurity
                .headers().cacheControl();
        httpSecurity
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

