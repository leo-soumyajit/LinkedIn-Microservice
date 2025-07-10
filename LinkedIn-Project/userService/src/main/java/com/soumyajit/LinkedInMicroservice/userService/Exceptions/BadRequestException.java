package com.soumyajit.LinkedInMicroservice.userService.Exceptions;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String message) {
    super(message);
  }
}
