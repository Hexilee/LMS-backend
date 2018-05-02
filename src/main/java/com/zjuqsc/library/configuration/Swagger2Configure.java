package com.zjuqsc.library.configuration;

import com.zjuqsc.library.config.SwaggerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Li Chenxi
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerConfig.class)
public class Swagger2Configure {
    private SwaggerConfig swagger;

    public Swagger2Configure(SwaggerConfig swagger) {
        this.swagger = swagger;
    }

    @Bean
    public Docket documentation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zjuqsc.library"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/api");
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
