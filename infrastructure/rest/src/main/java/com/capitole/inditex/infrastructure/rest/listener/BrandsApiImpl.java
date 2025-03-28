package com.capitole.inditex.infrastructure.rest.listener;

import com.capitole.inditex.application.ports.input.GetPriceUseCase;
import com.capitole.inditex.infrastructure.rest.BrandsApi;
import com.capitole.inditex.infrastructure.rest.mapper.ControllerMapper;
import com.capitole.inditex.infrastructure.rest.model.PriceDto;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BrandsApiImpl implements BrandsApi {

  private final GetPriceUseCase getPriceUseCase;
  private final ControllerMapper mapper;

  @Override
  public ResponseEntity<List<PriceDto>> _brandsBrandIdProductsProductIdGet(Long brandId,
      Long productId, OffsetDateTime applicationDate) {

    log.debug("[BrandsApi] -> [_brandsBrandIdProductsProductIdGet]: Retrieving the prices associated to: brandId {}, productId {} and application date {}", brandId, productId, applicationDate);

    final var result = this.getPriceUseCase.execute(GetPriceUseCase.InputValues.builder()
        .productId(productId)
        .brandId(brandId)
        .applicationDate(Optional.ofNullable(applicationDate).map(OffsetDateTime::toLocalDateTime))
        .build());

    return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(this.mapper.toDtoCollection(result));
  }
}
