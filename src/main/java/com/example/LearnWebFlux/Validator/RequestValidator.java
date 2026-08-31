package com.example.LearnWebFlux.Validator;

import com.example.LearnWebFlux.DTO.UserRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RequestValidator {
    private final Validator validator;

    public <T> Mono<T> validate(T userRequest) {
        Set<ConstraintViolation<T>> violations = validator.validate(userRequest);

        if(!violations.isEmpty()){
            Map<String, String> error = violations.stream()
                    .collect(Collectors.toMap(
                            v -> v.getPropertyPath().toString(),
                            ConstraintViolation::getMessage,
                            (a, b) -> a
                    ));

            throw new ValidationException(error.toString());
        }
        return Mono.just(userRequest);
    }
}
