package com.example.LearnWebFlux.Repository;

import com.example.LearnWebFlux.Entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
}
