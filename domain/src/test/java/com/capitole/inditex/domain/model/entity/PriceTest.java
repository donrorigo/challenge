package com.capitole.inditex.domain.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriceTest {

  @Test
  @DisplayName("Given valid parameters, when creating a Price, then the Price should be created successfully.")
  void testValidPriceCreation() {
    // Given
    Long brandId = 1L;
    Long productId = 35455L;
    Integer priceListId = 1;
    LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 0, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2020, 12, 31, 23, 59, 59);
    Integer priority = 1;
    BigDecimal price = BigDecimal.valueOf(35.50);
    String currency = "EUR";

    // When
    Price priceInstance = Price.builder()
        .brandId(brandId)
        .productId(productId)
        .priceListId(priceListId)
        .startDate(startDate)
        .endDate(endDate)
        .priority(priority)
        .amount(price)
        .currency(currency)
        .build();

    // Then
    assertNotNull(priceInstance);
    assertEquals(brandId, priceInstance.getBrandId());
    assertEquals(productId, priceInstance.getProductId());
    assertEquals(priceListId, priceInstance.getPriceListId());
    assertEquals(currency, priceInstance.getMoney().getCurrencyUnit().getCode());
    assertEquals(0,priceInstance.getMoney().getAmount().compareTo(price));
  }

  @Test
  @DisplayName("Given a start date after the end date, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithStartDateAfterEndDate() {
    LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 18, 0, 0);
    LocalDateTime endDate = LocalDateTime.of(2020, 6, 14, 15, 0, 0);

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceListId(1)
        .startDate(startDate)
        .endDate(endDate)
        .priority(1)
        .amount(BigDecimal.valueOf(35.50))
        .currency("EUR")
        .build());
  }

  @Test
  @DisplayName("Given a negative price, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithNegativePrice() {
    BigDecimal negativePrice = BigDecimal.valueOf(-35.50);

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceListId(1)
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(1)
        .amount(negativePrice)
        .currency("EUR")
        .build());
  }

  @Test
  @DisplayName("Given a null brandId, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithNullBrandId() {
    Long brandId = null;

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(brandId)  // Null value here
        .productId(35455L)
        .priceListId(1)
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(1)
        .amount(BigDecimal.valueOf(35.50))
        .currency("EUR")
        .build());
  }

  @Test
  @DisplayName("Given a null productId, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithNullProductId() {
    Long productId = null;

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(1L)
        .productId(productId)  // Null value here
        .priceListId(1)
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(1)
        .amount(BigDecimal.valueOf(35.50))
        .currency("EUR")
        .build());
  }

  @Test
  @DisplayName("Given a null priceListId, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithNullPriceListId() {
    Integer priceListId = null;

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceListId(priceListId)  // Null value here
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(1)
        .amount(BigDecimal.valueOf(35.50))
        .currency("EUR")
        .build());
  }

  @Test
  @DisplayName("Given a null startDate, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithNullStartDate() {
    LocalDateTime startDate = null;

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceListId(1)
        .startDate(startDate)  // Null value here
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(1)
        .amount(BigDecimal.valueOf(35.50))
        .currency("EUR")
        .build());
  }

  @Test
  @DisplayName("Given a null currency, when creating a Price, then an IllegalArgumentException should be thrown.")
  void testPriceCreationWithNullCurrency() {
    String currency = null;

    assertThrows(IllegalArgumentException.class, () -> Price.builder()
        .brandId(1L)
        .productId(35455L)
        .priceListId(1)
        .startDate(LocalDateTime.of(2020, 6, 14, 0, 0, 0))
        .endDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59))
        .priority(1)
        .amount(BigDecimal.valueOf(35.50))
        .currency(currency)  // Null value here
        .build());
  }

}