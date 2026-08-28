package com.example.LearnWebFlux.Controller;

import com.example.LearnWebFlux.DTO.UserRequest;
import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok) // will give you a new Mono
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping()
    public Flux<UserResponse> getUserById(){
        return userService.findAllUsers();
    }

    @PostMapping
    public Mono<ResponseEntity<UserResponse>> createUserById(@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(user));
    }
}
