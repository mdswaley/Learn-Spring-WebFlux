package com.example.LearnWebFlux.Error;

public class ExternalServiceException extends Exception{
    public ExternalServiceException(String productServiceUnavailable) {
        super(productServiceUnavailable);
    }
}
