package com.zjuqsc.library.configuration;

import com.zjuqsc.library.config.SwaggerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

/**
 * @author Li Chenxi
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfig.class)
public class Swagger2Configure {
    private static final ResponseMessage CONFLICT_RESPONSE_MESSAGE = new ResponseMessageBuilder()
            .code(HttpStatus.CONFLICT.value())
            .message(HttpStatus.CONFLICT.getReasonPhrase())
            .build();

    private static final ResponseMessage REQUEST_INVALIDE_MESSAGE = new ResponseMessageBuilder()
            .code(HttpStatus.BAD_REQUEST.value())
            .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
            .build();

    private SwaggerConfig swagger;

    public Swagger2Configure(SwaggerConfig swagger) {
        this.swagger = swagger;
    }

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST,
                        Arrays.asList(CONFLICT_RESPONSE_MESSAGE, REQUEST_INVALIDE_MESSAGE)
                )
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zjuqsc.library"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        SwaggerConfig.Info info = swagger.getInfo();
        return new ApiInfoBuilder()
                .version(info.getVersion())
                .title(info.getTitle())
                .description(info.getDescription())
                .license(info.getLicense())
                .licenseUrl(info.getLicenseUrl())
                .contact(new Contact(
                        info.getContactName(),
                        info.getContactURL(),
                        info.getContactEmail()
                ))
                .build();
    }
}
