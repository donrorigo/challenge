package com.capitole.inditex.application.ports.input;

import com.capitole.inditex.application.exception.InvalidFilterParametersException;
import com.capitole.inditex.application.ports.input.GetPriceUseCase.InputValues;
import com.capitole.inditex.domain.model.entity.Price;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

public interface GetPriceUseCase extends GeneralUseCase<InputValues, Collection<Price>> {

  @Override
  Collection<Price> execute(InputValues input);

  @Data
  class InputValues
  {
    private Long productId;
    private Long brandId;
    private Optional<LocalDateTime> applicationDate;

    @Builder
    public InputValues(Long productId, Long brandId, Optional<LocalDateTime> applicationDate) {
      if (productId == null && brandId == null)
      {
        throw new InvalidFilterParametersException("Cannot instance GetPriceUseCase.InputValues because there are no brand and product given");
      }
      this.productId = productId;
      this.brandId = brandId;
      this.applicationDate = applicationDate;
    }
  }
}
