Feature: Retrieve applicable prices
  As a client of the pricing service
  I want to retrieve the applicable price for a given product, brand, and application date
  So that I can confirm the correct tariff is applied according to the pricing rules

  Scenario: Test 1 - Request at 10:00 on day 14 for product 35455, brand 1 (ZARA)
    Given a pricing database with the following entries:
      | BRAND_ID | START_DATE              | END_DATE                | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE  | CURR |
      | 1        | 2020-06-14T00:00:00     | 2020-12-31T23:59:59     | 1          | 35455      | 0        | 35.50  | EUR  |
      | 1        | 2020-06-14T15:00:00     | 2020-06-14T18:30:00     | 2          | 35455      | 1        | 25.45  | EUR  |
      | 1        | 2020-06-15T00:00:00     | 2020-06-15T11:00:00     | 3          | 35455      | 1        | 30.50  | EUR  |
      | 1        | 2020-06-15T16:00:00     | 2020-12-31T23:59:59     | 4          | 35455      | 1        | 38.95  | EUR  |
    When I request the applicable price for brand "1", product "35455", at "2020-06-14T10:00:00Z"
    Then the service returns the price from tariff "1" with amount "35.50" EUR

  Scenario: Test 2 - Request at 16:00 on day 14 for product 35455, brand 1 (ZARA)
    Given a pricing database with the following entries:
      | BRAND_ID | START_DATE              | END_DATE                | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE  | CURR |
      | 1        | 2020-06-14T00:00:00     | 2020-12-31T23:59:59     | 1          | 35455      | 0        | 35.50  | EUR  |
      | 1        | 2020-06-14T15:00:00     | 2020-06-14T18:30:00     | 2          | 35455      | 1        | 25.45  | EUR  |
      | 1        | 2020-06-15T00:00:00     | 2020-06-15T11:00:00     | 3          | 35455      | 1        | 30.50  | EUR  |
      | 1        | 2020-06-15T16:00:00     | 2020-12-31T23:59:59     | 4          | 35455      | 1        | 38.95  | EUR  |
    When I request the applicable price for brand "1", product "35455", at "2020-06-14T16:00:00Z"
    Then the service returns the price from tariff "2" with amount "25.45" EUR

  Scenario: Test 3 - Request at 21:00 on day 14 for product 35455, brand 1 (ZARA)
    Given a pricing database with the following entries:
      | BRAND_ID | START_DATE              | END_DATE                | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE  | CURR |
      | 1        | 2020-06-14T00:00:00     | 2020-12-31T23:59:59     | 1          | 35455      | 0        | 35.50  | EUR  |
      | 1        | 2020-06-14T15:00:00     | 2020-06-14T18:30:00     | 2          | 35455      | 1        | 25.45  | EUR  |
      | 1        | 2020-06-15T00:00:00     | 2020-06-15T11:00:00     | 3          | 35455      | 1        | 30.50  | EUR  |
      | 1        | 2020-06-15T16:00:00     | 2020-12-31T23:59:59     | 4          | 35455      | 1        | 38.95  | EUR  |
    When I request the applicable price for brand "1", product "35455", at "2020-06-14T21:00:00Z"
    Then the service returns the price from tariff "1" with amount "35.50" EUR

  Scenario: Test 4 - Request at 10:00 on day 15 for product 35455, brand 1 (ZARA)
    Given a pricing database with the following entries:
      | BRAND_ID | START_DATE              | END_DATE                | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE  | CURR |
      | 1        | 2020-06-14T00:00:00     | 2020-12-31T23:59:59     | 1          | 35455      | 0        | 35.50  | EUR  |
      | 1        | 2020-06-14T15:00:00     | 2020-06-14T18:30:00     | 2          | 35455      | 1        | 25.45  | EUR  |
      | 1        | 2020-06-15T00:00:00     | 2020-06-15T11:00:00     | 3          | 35455      | 1        | 30.50  | EUR  |
      | 1        | 2020-06-15T16:00:00     | 2020-12-31T23:59:59     | 4          | 35455      | 1        | 38.95  | EUR  |
    When I request the applicable price for brand "1", product "35455", at "2020-06-15T10:00:00Z"
    Then the service returns the price from tariff "3" with amount "30.50" EUR

  Scenario: Test 5 - Request at 21:00 on day 16 for product 35455, brand 1 (ZARA)
    Given a pricing database with the following entries:
      | BRAND_ID | START_DATE              | END_DATE                | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE  | CURR |
      | 1        | 2020-06-14T00:00:00     | 2020-12-31T23:59:59     | 1          | 35455      | 0        | 35.50  | EUR  |
      | 1        | 2020-06-14T15:00:00     | 2020-06-14T18:30:00     | 2          | 35455      | 1        | 25.45  | EUR  |
      | 1        | 2020-06-15T00:00:00     | 2020-06-15T11:00:00     | 3          | 35455      | 1        | 30.50  | EUR  |
      | 1        | 2020-06-15T16:00:00     | 2020-12-31T23:59:59     | 4          | 35455      | 1        | 38.95  | EUR  |
    When I request the applicable price for brand "1", product "35455", at "2020-06-16T21:00:00Z"
    Then the service returns the price from tariff "4" with amount "38.95" EUR
