package com.capitole.inditex.domain.model.valueobject;

import com.capitole.inditex.domain.model.exception.InvalidFilterParametersException;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class PriceFilter {

  private final Long brandId;
  private final Long productId;
  private final LocalDateTime applicationDate;


  @Builder
  public PriceFilter(Long brandId, Long productId, LocalDateTime applicationDate) {
    if (brandId == null && productId == null)
    {
      throw new InvalidFilterParametersException("Cannot instance PriceFilter because there are no brand and product given");
    }
    this.brandId = brandId;
    this.productId = productId;
    this.applicationDate = applicationDate;
  }
}
