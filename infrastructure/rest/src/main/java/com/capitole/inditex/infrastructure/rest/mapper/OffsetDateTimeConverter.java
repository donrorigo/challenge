package com.capitole.inditex.infrastructure.rest.mapper;

import com.capitole.inditex.application.exception.InvalidDateFormatException;
import com.capitole.inditex.infrastructure.rest.utils.RestUtils;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OffsetDateTimeConverter implements Converter<String, OffsetDateTime> {

  private final Tracer tracer;
  private final RestUtils restUtils;

  @Override
  public OffsetDateTime convert(final String source) {
    Span span = tracer.spanBuilder("parseDate").startSpan();
    restUtils.setTraceId(span.getSpanContext().getTraceId());
    try {
      return OffsetDateTime.parse(source, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    } catch (RuntimeException e){
      throw new InvalidDateFormatException(e.getMessage());
    }
    finally {
      span.end();
    }
  }
}
