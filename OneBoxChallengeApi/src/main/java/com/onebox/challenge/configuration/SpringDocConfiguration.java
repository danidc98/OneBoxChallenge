package com.onebox.challenge.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfiguration {

    @Bean(name = "com.onebox.challenge.configuration.SpringDocConfiguration.apiInfo")
    OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("OneBox E-commerce Challenge")
                                .description("This API manages creation, deletion and modification of carts.")
                                .contact(
                                        new Contact()
                                                .name("Daniel del Canto de Guzmán")
                                                .email("ddcg98@gmail.com")
                                )
                                .version("1.0.0")
                )
        ;
    }
}