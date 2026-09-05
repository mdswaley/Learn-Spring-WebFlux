package com.example.LearnWebFlux.Service;

import com.example.LearnWebFlux.DTO.UserRequest;
import com.example.LearnWebFlux.DTO.UserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserResponse> getUserById(Long id);

    Flux<UserResponse> findAllUsers();

    Mono<UserResponse> createUser(UserRequest userRequest);

}
