package com.capitole.inditex.infrastructure.h2.adapter;

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
    if (filter.hasBaseFilters() && !this.repository.existsByBrandIdAndProductId(filter.brandId(),
        filter.productId()))
    {
      throw new RuntimeException("No price found for the given brandId and productId.");
    }
    return this.mapper.toDomainCollection(this.repository.findAll(PriceSpecification.withFilters(filter)));
  }
}
