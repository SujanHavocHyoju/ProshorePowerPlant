package com.proshore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

/**
 * Configuration for Swagger
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        Contact contact = new Contact();
        contact.setEmail("sujanhyoju@gmail.com");
        contact.setName("Sujan Hyoju");
        
        License myLicense = new License()
        						.name("MY License")
        						.url("https://choosealicense.com/licenses/my/");
        
        // Defining project info
        Info info = new Info()
        		.title("Proshore Power Plant API")
                .version("1.0")
                .contact(contact)
                .description("This API was created as a technical assessment task and exposes endpoints to manage Batteries.")
                .license(myLicense);
        
        return new OpenAPI().info(info);
    }
    
}
