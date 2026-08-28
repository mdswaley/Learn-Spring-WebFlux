package com.example.LearnWebFlux.Controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/benchmark")
public class BenchMarkController {
    @SneakyThrows
    @GetMapping("/users/{id}")
    public String getUser(@PathVariable Long id){
        Thread.sleep(1000);
        return "full users : "+id;
    }
}
