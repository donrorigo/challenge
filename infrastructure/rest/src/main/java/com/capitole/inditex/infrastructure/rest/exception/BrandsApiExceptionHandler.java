package com.capitole.inditex.infrastructure.rest.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BrandsApiExceptionHandler {

  @Autowired
  private HttpServletRequest request;

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleBadRequest(RuntimeException ex) throws URISyntaxException {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    this.processGeneralFields(ex, problemDetail);
    problemDetail.setType(new URI("https://api.example.com/errors/bad-request"));
    return problemDetail;
  }

  private void processGeneralFields(RuntimeException ex, ProblemDetail problemDetail)
      throws URISyntaxException {
    String requestUri = request.getRequestURI();
    URI uri = new URI(requestUri);
    problemDetail.setInstance(uri);

    String method = request.getMethod();
    problemDetail.setDetail("Method: " + method + " - Reason: " + ex.getMessage());

    String queryString = request.getParameterMap().entrySet().stream()
        .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
        .collect(Collectors.joining("&"));
    problemDetail.setProperties(Map.of("query", queryString));
  }

}
