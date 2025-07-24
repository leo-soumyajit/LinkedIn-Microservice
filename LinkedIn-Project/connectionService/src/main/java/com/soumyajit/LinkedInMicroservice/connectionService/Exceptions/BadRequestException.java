package com.soumyajit.LinkedInMicroservice.connectionService.Exceptions;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
