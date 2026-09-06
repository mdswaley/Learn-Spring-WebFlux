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
import reactor.util.retry.Retry;

import javax.naming.ServiceUnavailableException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

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
                .bodyToMono(Product.class)
                .delayElement(Duration.ofSeconds(1)) // wait for given time then give the data
                .timeout(Duration.ofSeconds(3))
                .retryWhen(Retry.backoff(3, Duration.ofMillis(500)) // retry to get data for 3 times and delay of every retry is double like -> 1st (500 ms) -> 2nd (1000 ms) -> 3rd (2000 ms)
                        .filter(ex -> ex instanceof ServiceUnavailableException) // only if it is ServiceUnavailableException
                        .onRetryExhaustedThrow((spec, signal) ->  // after 3 time retry throw this exception
                                new ServiceUnavailableException("Product service down after retries")))
                .onErrorMap(TimeoutException.class, // if you didn't get the product from given time it will throw this exception.
                        ex -> new ServiceUnavailableException("product service timeout"));
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productWebClient.get()
                .uri("/products")
                .retrieve()
                .bodyToFlux(Product.class);
    }
}
