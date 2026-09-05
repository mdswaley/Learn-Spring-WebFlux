package com.example.LearnWebFlux.Service;

import com.example.LearnWebFlux.DTO.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImp implements ProductService{

    private final WebClient productWebClient;

    @Override
    public Mono<Product> getProductById(Long id) {

        return productWebClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(Product.class);
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
