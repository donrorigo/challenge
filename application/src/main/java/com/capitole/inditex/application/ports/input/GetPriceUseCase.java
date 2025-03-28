package com.capitole.inditex.application.ports.input;

import com.capitole.inditex.application.ports.input.GetPriceUseCase.InputValues;
import com.capitole.inditex.domain.model.entity.Price;
import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;

public interface GetPriceUseCase extends GeneralUseCase<InputValues, Collection<Price>> {

  @Override
  Collection<Price> execute(InputValues input);

  @Data
  @Builder
  class InputValues
  {
    private Long productId;
    private Long brandId;
    private LocalDateTime applicationDate;
  }
}
