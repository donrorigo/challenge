package com.capitole.inditex.infrastructure.h2.adapter;

import com.capitole.inditex.application.exception.PriceNotFoundException;
import com.capitole.inditex.application.ports.output.PriceRepository;
import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.domain.model.valueobject.PriceFilter;
import com.capitole.inditex.infrastructure.h2.mapper.RepositoryMapper;
import com.capitole.inditex.infrastructure.h2.repository.H2PriceRepository;
import com.capitole.inditex.infrastructure.h2.repository.PriceSpecification;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

  private final H2PriceRepository repository;
  private final RepositoryMapper mapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Price> findApplicablePrice(PriceFilter filter) {
    return this.mapper.toDomainCollection(this.repository.findAll(PriceSpecification.withFilters(filter)));
  }

  @Override
  public void validatePricePresent(final Long brandId, final Long productId) {
    if (!this.repository.existsByBrandIdAndProductId(brandId, productId))
    {
      throw new PriceNotFoundException("No price found for the given brandId and productId.");
    }
  }
}
