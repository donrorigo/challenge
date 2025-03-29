package com.capitole.inditex.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PriceNotFoundException extends RuntimeException{
  public static final String code = "PRICE-000";
  private final String details;
}
