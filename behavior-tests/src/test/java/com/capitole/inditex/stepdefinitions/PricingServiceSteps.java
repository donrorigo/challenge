package com.capitole.inditex.stepdefinitions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.capitole.inditex.infrastructure.rest.model.PriceDto;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
public class PricingServiceSteps {

  private static final String BASE_URL = "http://localhost:8080/brands/%s/products/%s?application_date=%s";
  private ResponseEntity<List<PriceDto>> response;

  private final RestTemplate restTemplate = new RestTemplate();

  @Given("a pricing database with the following entries:")
  public void a_pricing_database_with_the_following_entries(DataTable dataTable) {
    // database is already filled with liquibase script
  }

  @When("I request the applicable price for brand {string}, product {string}, at {string}")
  public void i_request_the_applicable_price(String brandId, String productId, String dateTime) {
    String url = String.format(BASE_URL, brandId, productId, dateTime);
    response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<PriceDto>>() {});
  }

  @Then("the service returns the price from tariff {string} with amount {string} EUR")
  public void the_service_returns_the_price_from_tariff(String expectedTariff, String expectedPrice) {
    assertEquals(200, response.getStatusCode().value(), "Expected HTTP 200 OK");

    List<PriceDto> prices = Optional.ofNullable(response.getBody())
        .orElseThrow(() -> new AssertionError("Response body is null"));

    // Convertir los valores esperados
    int expectedTariffId = Integer.parseInt(expectedTariff);
    double expectedPriceValue = Double.parseDouble(expectedPrice);

    // Buscar el precio esperado
    Optional<PriceDto> matchingPrice = prices.stream()
        .filter(price -> price.getPriceListId() == expectedTariffId && price.getPrice() == expectedPriceValue)
        .findFirst();

    assertAll(
        () -> assertFalse(prices.isEmpty(), "Response list should not be empty"),
        () -> assertThat(matchingPrice).isPresent().withFailMessage("No matching price found for tariff %s with price %s EUR", expectedTariff, expectedPrice)
    );
  }
}
