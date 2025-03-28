package com.capitole.inditex.domain.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Data
@EqualsAndHashCode
public class Price {

  private final Long brandId;
  private final Long productId;
  private final Integer priceListId;
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;
  private final Integer priority;
  private final Money money;

  @Builder
  private Price(Long brandId,
      Long productId,
      Integer priceListId,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Integer priority,
      BigDecimal amount,
      String currency) {

    Validator.validate(brandId, productId, priceListId, startDate, endDate, priority, amount, currency);
    this.money = Money.of(CurrencyUnit.of(currency), amount);
    this.brandId = brandId;
    this.productId = productId;
    this.priceListId = priceListId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.priority = priority;
  }

  private static class Validator {
    static void validate(Long brandId,
        Long productId,
        Integer priceListId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priority,
        BigDecimal amount,
        String currency) {

      if (brandId == null || productId == null || priceListId == null || startDate == null ||
          endDate == null || priority == null || amount == null || currency == null) {
        throw new IllegalArgumentException("[Price] -> [validate]: Cannot instance null values");
      }

      if (startDate.isAfter(endDate)) {
        throw new IllegalArgumentException("[Price] -> [validate]: Cannot instance because the start date cannot be after the end date.");
      }

      if (CurrencyUnit.registeredCurrencies().stream().noneMatch(currencyUnit -> currencyUnit.getCode().equals(currency)))
      {
        throw new IllegalArgumentException("[Price] -> [validate]: Cannot instance with unknown currency");
      }

      if (amount.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException("[Price] -> [validate]: Cannot instance because the amount cannot be negative");
      }
    }
  }

}
