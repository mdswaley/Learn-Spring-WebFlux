package com.example.LearnWebFlux.Service;

import com.example.LearnWebFlux.DTO.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {
    Mono<Product> getProductById(Long id);
    Flux<Product> getAllProducts();
}
