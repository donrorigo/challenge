package com.capitole.inditex.infrastructure.h2.adapter;

import static org.junit.jupiter.api.Assertions.*;

import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.domain.model.valueobject.PriceFilter;
import com.capitole.inditex.infrastructure.h2.entity.PriceEntity;
import com.capitole.inditex.infrastructure.h2.mapper.RepositoryMapperImpl;
import com.capitole.inditex.infrastructure.h2.repository.H2PriceRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = {PriceRepositoryImplIntegrationTest.TestConfig.class})
@Import({PriceRepositoryImpl.class, RepositoryMapperImpl.class})
class PriceRepositoryImplIntegrationTest {

  @SpringBootApplication
  @EnableJpaRepositories("com.capitole.inditex.infrastructure.h2.repository")
  @EntityScan("com.capitole.inditex.infrastructure.h2.entity")
  static class TestConfig {
  }

  @Autowired
  private PriceRepositoryImpl priceRepositoryImpl;

  @Autowired
  private H2PriceRepository h2PriceRepository;

  @Test
  @DisplayName("""
    Given: A PriceEntity is created and saved in the H2 database.
    And: A PriceFilter is built for a date within the valid range.
    When: The repository is queried through the custom implementation.
    Then: It is verified that at least one price is returned.
    """)
  void testFindApplicablePrice() {
    PriceEntity priceEntity = new PriceEntity();
    priceEntity.setBrandId(1L);
    priceEntity.setProductId(35455L);
    priceEntity.setPriceListId(1);
    priceEntity.setPriority(1);
    priceEntity.setStartDate(LocalDateTime.of(2020, 6, 14, 0, 0));
    priceEntity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59, 59));
    priceEntity.setAmount(new BigDecimal("35.50"));
    priceEntity.setCurrency("EUR");
    h2PriceRepository.save(priceEntity);

    PriceFilter filter = PriceFilter.builder()
        .applicationDate(LocalDateTime.of(2020, 6, 15, 10, 0))
        .brandId(1L)
        .productId(35455L)
        .build();

    Collection<Price> prices = priceRepositoryImpl.findApplicablePrice(filter);

    assertFalse(prices.isEmpty());
    assertTrue(prices.stream().anyMatch(price -> price.getBrandId().equals(priceEntity.getBrandId()) && price.getProductId().equals(priceEntity.getProductId())));
  }

  @Test
  @DisplayName("""
    Given: A PriceFilter is created with non-existing brandId and productId.
    When: The repository is queried through the custom implementation.
    Then: A RuntimeException is expected to be thrown with a message indicating no price was found.
    """)
  void testFindApplicablePriceThrowsExceptionWhenNoPriceFound() {

    PriceFilter filter = PriceFilter.builder()
        .applicationDate(LocalDateTime.of(2020, 6, 15, 10, 0))
        .brandId(999L)  // brand does not exist in database
        .productId(999L) // product does not exist in database
        .build();

    RuntimeException exception = assertThrows(RuntimeException.class, () -> priceRepositoryImpl.findApplicablePrice(filter));

    assertEquals("No price found for the given brandId and productId.", exception.getMessage());
  }

  @Test
  @DisplayName("""
    Given: A PriceEntity is created and saved in the H2 database with a specific brandId, productId, and date range.
    And: A PriceFilter is built for an applicationDate that is outside the range.
    When: The repository is queried through the custom implementation.
    Then: An empty list is expected to be returned as no prices match the applicationDate.
    """)
  void testFindApplicablePriceReturnsEmptyListWhenNoPriceFoundForApplicationDate() {
    PriceEntity priceEntity1 = new PriceEntity();
    priceEntity1.setBrandId(1L);
    priceEntity1.setProductId(35455L);
    priceEntity1.setPriceListId(1);
    priceEntity1.setPriority(1);
    priceEntity1.setStartDate(LocalDateTime.of(2020, 6, 1, 0, 0));  // Start date en junio
    priceEntity1.setEndDate(LocalDateTime.of(2020, 6, 10, 23, 59, 59));  // End date en junio
    priceEntity1.setAmount(new BigDecimal("35.50"));
    priceEntity1.setCurrency("EUR");
    h2PriceRepository.save(priceEntity1);

    PriceFilter filter = PriceFilter.builder()
        .applicationDate(LocalDateTime.of(2020, 6, 15, 10, 0))  // Fecha fuera del rango
        .brandId(1L)  // brand exists
        .productId(35455L)  // product exists
        .build();

    Collection<Price> prices = assertDoesNotThrow(() -> priceRepositoryImpl.findApplicablePrice(filter));

    assertTrue(prices.isEmpty());
  }

}