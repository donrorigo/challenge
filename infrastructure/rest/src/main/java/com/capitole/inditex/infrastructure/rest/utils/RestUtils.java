package com.capitole.inditex.infrastructure.rest.utils;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class RestUtils {

  private String traceId;
}
