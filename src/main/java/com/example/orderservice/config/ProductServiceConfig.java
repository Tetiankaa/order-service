package com.example.orderservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.ApiClient;
import org.example.event.api.IOnProductCreatedConsumerService;
import org.example.event.model.ProductCreatedPayload;
import org.example.rest.ProductApi;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ProductServiceConfig {

    private final TokenPropagationHandler tokenPropagationHandler;
    @Bean
    public ProductApi productApi(){
        return new ProductApi(authApiClient());
    }

    @Bean
    public ApiClient customApiClient(){
        RestTemplate restTemplate = restTemplateBuilder().build();
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setDebugging(true);
        apiClient.setBasePath("http://localhost:8080/api/v1");

        return apiClient;
    }

    @Bean
    public ApiClient authApiClient(){
        ApiClient apiClient = new ApiClient();
        apiClient.setBearerToken(() -> {
            JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
           return authenticationToken.getToken().getTokenValue();

        });

        return apiClient;
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder()
                .interceptors(tokenPropagationHandler);

    }



}
