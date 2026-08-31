package com.example.LearnWebFlux.Error;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String res, Long id){
        super("Resource: "+res+" not found with id: "+id);
    }
}
