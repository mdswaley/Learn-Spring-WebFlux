package com.example.LearnWebFlux.Controller;

import com.example.LearnWebFlux.DTO.Product;
import com.example.LearnWebFlux.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable Long id){
        return productService.getProductById(id)
                .map(ResponseEntity::ok);
    }

    @GetMapping
    public Flux<ResponseEntity<Product>> getAllProducts(){
        return productService.getAllProducts()
                .map(ResponseEntity::ok);
    }
}
