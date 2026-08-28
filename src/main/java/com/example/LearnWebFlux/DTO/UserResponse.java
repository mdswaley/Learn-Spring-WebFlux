package com.example.LearnWebFlux.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
