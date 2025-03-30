package com.capitole.inditex.infrastructure.rest.config;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.jaeger.JaegerGrpcSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenTelemetryConfig {

  @Bean
  public OpenTelemetry openTelemetry() {

    JaegerGrpcSpanExporter jaegerExporter = JaegerGrpcSpanExporter.builder()
        .setEndpoint("http://localhost:14250")
        .build();


    SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
        .setResource(Resource.create(
            Attributes.of(
                AttributeKey.stringKey("service.name"), "pricing-service",
                AttributeKey.stringKey("service.version"), "1.0",
                AttributeKey.stringKey("host.name"), "localhost"
            )
        ))
        .addSpanProcessor(BatchSpanProcessor.builder(jaegerExporter).build())
        .build();

    return OpenTelemetrySdk.builder()
        .setTracerProvider(tracerProvider)
        .build();
  }

  @Bean
  public Tracer tracer(OpenTelemetry openTelemetry) {
    return openTelemetry.getTracer("pricing-service", "1.0");
  }
}
