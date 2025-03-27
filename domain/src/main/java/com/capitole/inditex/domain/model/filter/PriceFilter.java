package com.capitole.inditex.domain.model.filter;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PriceFilter(Long brandId, Long productId, LocalDateTime applicationDate) {}
