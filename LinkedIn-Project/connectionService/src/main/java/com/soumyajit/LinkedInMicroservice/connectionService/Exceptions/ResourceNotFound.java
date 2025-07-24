package com.soumyajit.LinkedInMicroservice.connectionService.Exceptions;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
}
