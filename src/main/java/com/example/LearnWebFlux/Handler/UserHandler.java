package com.example.LearnWebFlux.Handler;

import com.example.LearnWebFlux.DTO.UserRequest;
import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final UserService userService;

    public Mono<ServerResponse> getAll(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAllUsers(), UserResponse.class);
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserRequest.class)
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
