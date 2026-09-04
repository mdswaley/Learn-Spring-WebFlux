package com.example.LearnWebFlux.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .baseUrl("https://dummyjson.com/products")
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}

// Difference between RestClient and WebClient
/*
    RestTemplate is the traditional way to make HTTP calls in Spring. It is blocking.
    Every call holds the thread until the response arrives. In a WebFlux reactive
    pipeline, using RestTemplate blocks a Netty event loop thread, which is
    expensive. WebClient is the non-blocking, reactive replacement.

    when we are using spring mvc at that time you can use RestClient, and it is blocking in nature
    when we are using spring webflux need to use webClient. Bcz it is non-blocking


    With virtual threads and Spring MVC you can continue using RestTemplate
    or the newer RestClient without any issues. The thread blocks but the VT
    unmounts so no OS thread is wasted. WebClient is mandatory only in a
    WebFlux reactive pipeline where you cannot block under any
    circumstance.

*/
