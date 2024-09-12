package com.bjit.book_service.config;

import com.bjit.book_service.model.BookResponseModel;
import com.bjit.book_service.model.InventoryModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public InventoryModel inventoryModel() {
        return new InventoryModel();
    }

    @Bean
    public BookResponseModel bookResponseModel() {
        return new BookResponseModel();
    }

    @Bean
    public HttpHeaders httpHeaders() {
        return new HttpHeaders();
    }
}
