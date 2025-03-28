package com.capitole.inditex.infrastructure.rest.listener;

import com.capitole.inditex.application.ports.input.GetPriceUseCase;
import com.capitole.inditex.infrastructure.rest.BrandsApi;
import com.capitole.inditex.infrastructure.rest.mapper.ControllerMapper;
import com.capitole.inditex.infrastructure.rest.model.PriceDto;
import com.capitole.inditex.infrastructure.rest.utils.RestUtils;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BrandsApiImpl implements BrandsApi {

  private final GetPriceUseCase getPriceUseCase;
  private final ControllerMapper mapper;
  private final Tracer tracer;
  private final RestUtils restUtils;

  @Override
  public ResponseEntity<List<PriceDto>> _brandsBrandIdProductsProductIdGet(Long brandId,
      Long productId, OffsetDateTime applicationDate) {

    Span span = tracer.spanBuilder("getPrice").startSpan();
    restUtils.setTraceId(span.getSpanContext().getTraceId());

    try {
      span.addEvent("[BrandsApi] -> [_brandsBrandIdProductsProductIdGet]: new request received");
      log.info("Current Trace ID in controller {}", span.getSpanContext().getTraceId());

      final var result = this.getPriceUseCase.execute(GetPriceUseCase.InputValues.builder()
          .productId(productId)
          .brandId(brandId)
          .applicationDate(
              Optional.ofNullable(applicationDate).map(OffsetDateTime::toLocalDateTime))
          .build());

      span.addEvent("[BrandsApi] -> [_brandsBrandIdProductsProductIdGet]: use case has been executed, trying to map the result");

      return result.isEmpty() ? ResponseEntity.noContent().build()
          : ResponseEntity.ok(this.mapper.toDtoCollection(result));
      }
    finally
      {
        span.end();
      }
    }
}
