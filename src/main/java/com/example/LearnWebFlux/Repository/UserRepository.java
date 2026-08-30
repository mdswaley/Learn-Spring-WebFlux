package com.example.LearnWebFlux.Repository;

import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<Boolean> existsByEmail(String email);
}
