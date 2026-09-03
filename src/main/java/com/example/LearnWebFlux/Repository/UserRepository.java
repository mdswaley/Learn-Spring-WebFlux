package com.example.LearnWebFlux.Repository;

import com.example.LearnWebFlux.Entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long>, ReactiveSortingRepository<User, Long> {
    Mono<Boolean> existsByEmail(String email);
}
