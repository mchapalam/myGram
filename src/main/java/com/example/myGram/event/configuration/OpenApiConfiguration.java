package com.example.myGram.event.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    
    @Bean
    public OpenAPI openApiDescription(){
        Server localhostServer = new Server();

        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Server productionServer = new Server();
        productionServer.setUrl("http://some.prod.url");
        productionServer.setDescription("My production server");

        return new OpenAPI().servers(List.of(localhostServer, productionServer));
    }
}
