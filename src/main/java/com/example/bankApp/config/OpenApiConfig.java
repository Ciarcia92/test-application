package com.example.bankApp.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Lorenzo",
                        email = "email@email.com"
//                        url = "https://aliboucoding.com/course"
                ),
                description = "OpenApi documentation for Spring Security",
                title = "OpenApi specification - APPLICAZIONE BANKA",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8088/api/v1"
                )
//                @Server(
//                        description = "PROD ENV"
//                        url = "https://aliboucoding.com/course"
//                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",  // Descrizione dell'autenticazione
        type = SecuritySchemeType.HTTP,  // Utilizzi il tipo HTTP perché JWT è un tipo di autenticazione HTTP
        scheme = "bearer",  // Il tipo di schema è "bearer"
        bearerFormat = "JWT",  // Specifica che il formato del token è JWT
        in = SecuritySchemeIn.HEADER  // Il token viene passato nell'header Authorization
)
public class OpenApiConfig {
}