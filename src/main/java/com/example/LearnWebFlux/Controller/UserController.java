package com.example.LearnWebFlux.Controller;

import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public Mono<UserResponse> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping()
    public Flux<UserResponse> getUserById(){
        return userService.findAllUsers();
    }
}
