package de.telran.UrlShortener.configure;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "UrlShortener project Api",
                description = "API Url Shortener",
                version = "1.0.0",
                contact = @Contact(
                        name = "Natalia Kulikova",
                        email = "nata*******@gmail.com",
                        url = "https://github.com/Natacool/de-telran-diplom"
                )
        )
)
public class OpenApiConfig {
}
