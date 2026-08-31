package com.example.LearnWebFlux.Handler;

import com.example.LearnWebFlux.DTO.UserRequest;
import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Service.UserService;
import com.example.LearnWebFlux.Validator.RequestValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;
    private final RequestValidator validator;

    public Mono<ServerResponse> getAll(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAllUsers(), UserResponse.class);
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequest.class)
                .flatMap(validator::validate)
                .flatMap(userService::createUser)
                .flatMap(createUser -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(createUser));
    }



    public Mono<ServerResponse> getById(ServerRequest serverRequest) {
        Long id = parseId(serverRequest);
        return userService.getUserById(id)
                .flatMap(userResponse -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(userResponse));
    }

    private Long parseId(ServerRequest req){
        try{
            return Long.parseLong(req.pathVariable("id"));
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("Invalid id format.");
        }
    }
}
