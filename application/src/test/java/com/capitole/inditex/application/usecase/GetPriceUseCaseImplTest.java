package com.capitole.inditex.application.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.capitole.inditex.application.ports.input.GetPriceUseCase;
import com.capitole.inditex.application.ports.output.PriceRepository;
import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.domain.model.valueobject.PriceFilter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

class GetPriceUseCaseImplTest {

  @Mock
  private PriceRepository priceRepository;

  @InjectMocks
  private GetPriceUseCaseImpl getPriceUseCase;

  private PriceFilter.PriceFilterBuilder filterBuilder;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    filterBuilder = PriceFilter.builder();
    closeable = MockitoAnnotations.openMocks(this);
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  @DisplayName("""
    Given: Valid input values for applicationDate, brandId, and productId.
    When: Prices exist in the repository for the given filters.
    Then: The applicable prices are returned from the use case, and the repository's findApplicablePrice method is called once.
    """)
  void givenValidInputValues_whenPricesExist_thenReturnApplicablePrices() {
    // Given
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
    Long brandId = 1L;
    Long productId = 35455L;

    List<Price> expectedPrices = List.of(mock(Price.class));
    when(priceRepository.findApplicablePrice(any(PriceFilter.class))).thenReturn(expectedPrices);

    // When
    List<Price> actualPrices = (List<Price>) getPriceUseCase.execute(GetPriceUseCase.InputValues.builder()
        .applicationDate(Optional.of(applicationDate))
        .brandId(brandId)
        .productId(productId)
        .build());

    // Then
    assertEquals(expectedPrices, actualPrices);
    verify(priceRepository, times(1)).findApplicablePrice(any(PriceFilter.class));
  }

  @Test
  @DisplayName("""
    Given: Valid input values for applicationDate, brandId, and productId.
    When: No prices exist in the repository for the given filters.
    Then: An empty list is returned from the use case, and the repository's findApplicablePrice method is called once.
    """)
  void givenValidInputValues_whenNoPricesExist_thenReturnEmptyList() {
    // Given
    LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
    Long brandId = 1L;
    Long productId = 35455L;


    when(priceRepository.findApplicablePrice(any(PriceFilter.class))).thenReturn(List.of());

    // When
    List<Price> actualPrices = (List<Price>) getPriceUseCase.execute(GetPriceUseCase.InputValues.builder()
        .applicationDate(Optional.of(applicationDate))
        .brandId(brandId)
        .productId(productId)
        .build());

    // Then
    assertTrue(actualPrices.isEmpty());
    verify(priceRepository, times(1)).findApplicablePrice(any(PriceFilter.class));
  }
}