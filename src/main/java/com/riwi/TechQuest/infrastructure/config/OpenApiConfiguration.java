package com.riwi.TechQuest.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(
        title = "Tech Quest",
        version = "1.0",
        description = "This is a API of Tech Quest"
))
public class OpenApiConfiguration {
}
