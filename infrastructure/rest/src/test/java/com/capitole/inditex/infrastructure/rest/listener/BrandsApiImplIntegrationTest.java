package com.capitole.inditex.infrastructure.rest.listener;

import com.capitole.inditex.application.ports.input.GetPriceUseCase;
import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.infrastructure.rest.mapper.ControllerMapper;
import com.capitole.inditex.infrastructure.rest.model.PriceDto;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BrandsApiImplIntegrationTest {

  @SpringBootApplication(scanBasePackages = "com.capitole.inditex")
  static class TestConfig {
  }

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetPriceUseCase getPriceUseCase;

  @MockBean
  private ControllerMapper mapper;

  @InjectMocks
  private BrandsApiImpl brandsApi;

  @BeforeEach
  public void setUp() {
    // Setup any mocks or data needed before each test
  }

  @Test
  @DisplayName("""
    Given: A valid brandId, productId, and applicationDate.
    When: Prices are retrieved for the given parameters from the use case.
    Then: The applicable prices are returned with the correct price format and dates, including the expected precision for price and correct handling of date formats.
    """)
  void testGetPricesReturnsPrices() throws Exception {
    // Given
    Long brandId = 1L;
    Long productId = 35455L;
    OffsetDateTime applicationDate = OffsetDateTime.now();

    Price price = Price.builder()
        .brandId(brandId)
        .productId(productId)
        .priceListId(1)
        .priority(1)
        .startDate(applicationDate.toLocalDateTime().minusDays(1))
        .endDate(applicationDate.toLocalDateTime().plusDays(1))
        .amount(new BigDecimal("35.50"))
        .currency("EUR")
        .build();

    final var priceDto = PriceDto.builder()
        .price(price.getMoney().getAmount().doubleValue())
        .currency(price.getMoney().getCurrencyUnit().getCode())
        .priceListId( price.getPriceListId() )
        .startDate(price.getStartDate().atOffset(ZoneOffset.UTC))
        .endDate(price.getEndDate().atOffset(ZoneOffset.UTC))
        .build();

    when(getPriceUseCase.execute(any(GetPriceUseCase.InputValues.class))).thenReturn(List.of(price));
    when(mapper.toDtoCollection(List.of(price))).thenReturn(List.of(priceDto));

    mockMvc.perform(get("/brands/{brandId}/products/{productId}", brandId, productId)
            .param("applicationDate", applicationDate.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].price").value(35.50))
        .andExpect(jsonPath("$[0].currency").value(price.getMoney().getCurrencyUnit().getCode()))
        .andExpect(jsonPath("$[0].price_list_id").value(price.getPriceListId()));
  }

  @Test
  @DisplayName("""
    Given: Valid input values for applicationDate, brandId, and productId, but no prices are available for the given filters.
    When: The service is called to retrieve the applicable prices.
    Then: The response returns a 204 No Content status, indicating no applicable prices were found, and the response body is empty.
    """)
  void testGetPricesReturnsEmptyList() throws Exception {
    Long brandId = 1L;
    Long productId = 35455L;
    OffsetDateTime applicationDate = OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);

    // Mock the service call to return an empty list
    when(getPriceUseCase.execute(any(GetPriceUseCase.InputValues.class)))
        .thenReturn(Collections.emptyList());

    mockMvc.perform(get("/brands/{brandId}/products/{productId}", brandId, productId)
            .param("applicationDate", applicationDate.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""))
        .andExpect(jsonPath("$").doesNotExist());
  }

  @Test
  @DisplayName("""
    Given: A RuntimeException is thrown by the service.
    When: The exception is handled by the controller advice.
    Then: The response returns a 400 Bad Request status with the exception message in the body.
    """)
  void testRuntimeExceptionHandler() throws Exception {
    Long brandId = 1L;
    Long productId = 35455L;
    OffsetDateTime applicationDate = OffsetDateTime.of(2020, 6, 14, 10, 0, 0, 0, ZoneOffset.UTC);

    // Mock the service to throw a RuntimeException
    when(getPriceUseCase.execute(any(GetPriceUseCase.InputValues.class)))
        .thenThrow(new RuntimeException("Invalid input"));

    // Perform the GET request and check the response
    mockMvc.perform(get("/brands/{brandId}/products/{productId}", brandId, productId)
            .param("applicationDate", applicationDate.toString())
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()) // Expecting 400 Bad Request
        .andExpect(jsonPath("$.status").value(400)) // The status field should be 400
        .andExpect(jsonPath("$.title").value("Bad Request")) // Assuming you set a title in the ProblemDetail
        .andExpect(jsonPath("$.detail").value("Method: GET - Reason: Invalid input")) // The detail should match the exception message
        .andExpect(jsonPath("$.type").value("https://api.example.com/errors/bad-request")); // Example of an error type URI
  }

}