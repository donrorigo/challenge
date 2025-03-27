package com.capitole.inditex.application.usecase;

import com.capitole.inditex.application.ports.input.GetPriceUseCase;
import com.capitole.inditex.application.ports.output.PriceRepository;
import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.domain.model.filter.PriceFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class GetPriceUseCaseImpl implements GetPriceUseCase {

  private final PriceRepository priceRepository;

  @Override
  @Transactional(readOnly = true)
  public Price execute(InputValues input) {

    final var filters = PriceFilter.builder()
        .applicationDate(input.getApplicationDate())
        .brandId(input.getBrandId())
        .productId(input.getProductId())
        .build();

    return this.priceRepository.findApplicablePrice(filters).orElseThrow();
  }
}
