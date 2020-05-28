package com.epam.ahnl.controller.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    companion object SwaggerConfigConstants {
        const val API_TITLE = "Students management system REST-based CRUD with MS Azure CosmosDb"
        const val API_VERSION = "0.0.1-SNAPSHOT"
        const val API_DESCRIPTION = """
•  Build tool: Maven (multi-module project).
•  Application development platform: Spring Boot.
•  Database: MS Azure CosmosDb with MongoDb.
•  Data access: Spring Data MongoDB.
"""
        const val API_CREDITS = "Developed by: Maksim Chakur"
    }

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(buildApiInfo())
    }

    private fun buildApiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title(API_TITLE)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .license(API_CREDITS)
                .build()
    }
}