package com.example.LearnWebFlux.Service;

import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    @Override
    public Mono<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id : "+id)))
                .map(user -> UserResponse.from(user));
    }

    @Override
    public Flux<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .map(UserResponse::from);
    }
}
