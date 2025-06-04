package com.Java2.proyecto.diego.AlkemyDiego.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OpenApiConfig implements WebMvcConfigurer {
  private static final String SWAGGER_UI_PATH = "/swagger-ui.html";
  private static final String API_TITLE = "API de Productos by Diego Perez";
  private static final String API_VERSION = "1.0";
  private static final String API_DESCRIPTION = "API para gestión de productos ";
  private static final String SECURITY_SCHEME_NAME = "bearerAuth";

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", SWAGGER_UI_PATH);
    registry.addRedirectViewController("/swagger-ui", SWAGGER_UI_PATH);
    registry.addViewController("/swagger-ui/").setViewName("forward:/swagger-ui/index.html");
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title(API_TITLE)
            .version(API_VERSION)
            .description(API_DESCRIPTION))
        .addSecurityItem(new SecurityRequirement()
            .addList(SECURITY_SCHEME_NAME))
        .components(new Components()
            .addSecuritySchemes(SECURITY_SCHEME_NAME,
                new SecurityScheme()
                    .name(SECURITY_SCHEME_NAME)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")));
  }
}
