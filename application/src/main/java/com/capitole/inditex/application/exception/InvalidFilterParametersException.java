package com.capitole.inditex.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvalidFilterParametersException extends RuntimeException{
  public static final String code = "PRICE-001";
  private final String details;
}
