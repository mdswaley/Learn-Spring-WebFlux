package com.example.LearnWebFlux.Controller;

import com.example.LearnWebFlux.DTO.UserRequest;
import com.example.LearnWebFlux.DTO.UserResponse;
import com.example.LearnWebFlux.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

//@RestController // now we are using functional model for reactive. so we don't need controller
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
    public Mono<ResponseEntity<UserResponse>> createUserById(@Valid @RequestBody UserRequest userRequest){
        return userService.createUser(userRequest)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(user));
    }

//    Server-Sent Events (SSE)
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // TEXT_EVENT_STREAM:- I am going to send data as a
    // continuous stream of events, rather than one normal HTTP response.
    public Flux<UserResponse> streamUsers() {
        return userService.findAllUsers()
                .delayElements(Duration.ofSeconds(2)); // make delay of 2s to get data.
    }

    // In normal Spring MVC when you get all data at once. Then first from database all the data will store in
    // you JVM then once all data are present it will give to client. Although you get the data but the damage has already made

    /*
    the server can send:
      User1
        ↓
      User2
        ↓
      User3
        ↓
      User4
    as each item becomes available.*/

    /*
     Client
        │
        │ GET /users/stream
        ↓
     Controller
        │
        │ Flux<UserResponse>
        ↓
      Service
        │
        ↓
     Repository
        │
        ↓
     Database
        │
        │ User 1 ─────────→ Client
        │ User 2 ─────────→ Client
        │ User 3 ─────────→ Client
        │ User 4 ─────────→ Client

   */

}
