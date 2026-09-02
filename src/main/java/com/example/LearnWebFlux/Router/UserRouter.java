package com.example.LearnWebFlux.Router;

import com.example.LearnWebFlux.Error.ErrorResponse;
import com.example.LearnWebFlux.Error.ResourceNotFoundException;
import com.example.LearnWebFlux.Handler.UserHandler;
import jakarta.validation.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class UserRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return RouterFunctions.route()
                .GET("/api/v1/users", handler::getAll)
                .POST("/api/v1/users", handler::create)
                .GET("/api/v1/users/{id}", handler::getById)
//                .PUT("/api/v1/users/{id}", handler::update)
//                .DELETE("/api/v1/users/{id}", handler::delete)

                .onError(ResourceNotFoundException.class,
                        (ex, req) -> ServerResponse.status(HttpStatus.NOT_FOUND)
                                .bodyValue(new ErrorResponse(ex.getMessage())))
                .onError(ValidationException.class,
                        (ex, req) -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                .bodyValue(new ErrorResponse(ex.getMessage())))
                .onError(IllegalArgumentException.class,
                        (ex, req) -> ServerResponse.status(HttpStatus.BAD_REQUEST)
                                .bodyValue(new ErrorResponse(ex.getMessage())))
                .build();
    }
}