package com.capitole.inditex.domain.model.valueobject;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Builder
@Getter
@Accessors(fluent = true)
public final class PriceFilter {

  private final Long brandId;
  private final Long productId;
  private final LocalDateTime applicationDate;

  public boolean hasBaseFilters()
  {
    return this.brandId != null && this.productId != null;
  }
}
