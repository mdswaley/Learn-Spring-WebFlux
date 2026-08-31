package com.example.LearnWebFlux.Service;

import com.example.LearnWebFlux.DTO.UserRequest;
import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Entity.User;
import com.example.LearnWebFlux.Error.ResourceNotFoundException;
import com.example.LearnWebFlux.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    @Override
    public Mono<UserResponse> getUserById(Long id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResourceNotFoundException("User", id)))
                .map(user -> UserResponse.from(user));
    }

    @Override
    public Flux<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .map(UserResponse::from);
    }

    @Override
    public Mono<UserResponse> createUser(UserRequest userRequest) {
        return userRepository.existsByEmail(userRequest.getEmail())
                .flatMap(check -> {
                    if(check) return Mono.error(new IllegalArgumentException("User already exist with email "+userRequest.getEmail()));

                    User user = User.builder()
                            .name(userRequest.getName())
                            .email(userRequest.getEmail())
                            .role(userRequest.getRole())
                            .build();

                    return userRepository.save(user);
                })
                .map(UserResponse::from)
                .doOnSuccess(res -> log.info("Created user with id: "+res.getId()));
    }
}
