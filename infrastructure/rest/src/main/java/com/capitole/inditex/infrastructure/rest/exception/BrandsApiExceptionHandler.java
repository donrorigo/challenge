package com.capitole.inditex.infrastructure.rest.exception;

import com.capitole.inditex.application.exception.InvalidDateFormatException;
import com.capitole.inditex.application.exception.InvalidFilterParametersException;
import com.capitole.inditex.application.exception.PriceNotFoundException;
import com.capitole.inditex.infrastructure.rest.utils.RestUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class BrandsApiExceptionHandler {

  private final HttpServletRequest request;
  private final RestUtils restUtils;

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ProblemDetail handleBadRequest(RuntimeException ex) throws URISyntaxException {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    this.processGeneralFields(ex, problemDetail);
    problemDetail.setType(new URI("https://api.example.com/errors/internal-server-error"));
    return problemDetail;
  }

  @ExceptionHandler(PriceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail handleNotFound(PriceNotFoundException ex) throws URISyntaxException {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
    this.processGeneralFields(ex, problemDetail);
    problemDetail.setType(new URI("https://api.example.com/errors/not-found"));
    return problemDetail;
  }

  @ExceptionHandler(InvalidDateFormatException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleInvalidDateFormatException(InvalidDateFormatException ex) throws URISyntaxException {
    ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    this.processGeneralFields(ex, problemDetail);
    problemDetail.setType(new URI("https://api.example.com/errors/bad-request"));
    return problemDetail;
  }


  @ExceptionHandler(InvalidFilterParametersException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleInvalidFilterParametersException(InvalidFilterParametersException ex) throws URISyntaxException {
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
    problemDetail.setDetail("Method: " + method + " - Type: " + ex.getClass().getSimpleName() + " - Reason: " + ex.getMessage());

    String queryString = request.getParameterMap().entrySet().stream()
        .map(entry -> entry.getKey() + "=" + String.join(",", entry.getValue()))
        .collect(Collectors.joining("&"));
    problemDetail.setProperties(Map.of("query", queryString, "traceId", restUtils.getTraceId()));
  }

}
