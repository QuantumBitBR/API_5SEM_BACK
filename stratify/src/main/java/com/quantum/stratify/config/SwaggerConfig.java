package com.quantum.stratify.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.SecurityRequirement;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme()))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info().title("Stratify API").version("v1"));
    }

    private SecurityScheme securityScheme(){
        return new SecurityScheme()
                .name("Authorization")
                .description("Insira o token JWT no formato: Bearer <token>")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);
    }
}
