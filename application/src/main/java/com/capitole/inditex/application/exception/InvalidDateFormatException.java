package com.capitole.inditex.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidDateFormatException extends RuntimeException{
  public static final String code = "PRICE-002";
  private final String details;
}
