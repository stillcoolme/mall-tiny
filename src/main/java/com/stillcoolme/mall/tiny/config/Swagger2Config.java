package com.stillcoolme.mall.tiny.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>Swagger2API文档的配置</p>
 * 常用注解
 * @Api：用于修饰Controller类，生成Controller相关文档信息
 * @ApiOperation：用于修饰Controller类中的方法，生成接口方法相关文档信息
 * @ApiParam：用于修饰接口中的参数，生成接口参数相关文档信息
 * @ApiModelProperty：用于修饰实体类的属性，当实体类是请求参数或返回结果时，直接生成相关文档信息
 * 最后运行项目，访问 http://localhost:8080/swagger-ui.html
 * @author stillcoolme
 * @version V1.0.0
 * @date 2022/11/19 11:36
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    public static final String BRAND_TAG = "brand controller";

    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stillcoolme.mall.tiny.controller")) //为当前包下controller生成API文档
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) //为有@Api注解的Controller生成API文档
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)) //为有@ApiOperation注解的方法生成API文档
                .build()
                .tags(new Tag(BRAND_TAG, "商品品牌管理"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SwaggerUI演示")
                .description("mall-tiny")
                .contact(new Contact("stillcoolme", "", ""))
                .version("1.0")
                .build();
    }
}
