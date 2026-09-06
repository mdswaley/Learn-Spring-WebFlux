package com.example.LearnWebFlux.Service;

import com.example.LearnWebFlux.DTO.Product;
import com.example.LearnWebFlux.Error.BadRequestException;
import com.example.LearnWebFlux.Error.ExternalServiceException;
import com.example.LearnWebFlux.Error.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        res -> res.bodyToMono(String.class)
                                    .map(body -> {
                                        if(res.statusCode() == HttpStatus.NOT_FOUND){
                                            return new ResourceNotFoundException("Product", id);
                                        }

                                        return new BadRequestException("Bad request: "+body);
                                    })
                )
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
