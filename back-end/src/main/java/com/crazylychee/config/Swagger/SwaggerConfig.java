package com.crazylychee.config.Swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi1(Environment environment) {
        return new Docket(DocumentationType.SWAGGER_2).groupName("业务接口").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.crazylychee"))
                .build().globalOperationParameters(setHeaderToken());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(" ")
                .termsOfServiceUrl("")
                .description(" ")
                .version("1.0")
                .build();
    }


    private List<Parameter> setHeaderToken() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder userId = new ParameterBuilder();
        userId.name("token").description("用户令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pars.add(userId.build());

        return pars;
    }
}
