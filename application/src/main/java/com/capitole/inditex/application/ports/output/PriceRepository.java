package com.capitole.inditex.application.ports.output;

import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.domain.model.filter.PriceFilter;
import java.util.Optional;

public interface PriceRepository {
  Optional<Price> findApplicablePrice(PriceFilter filter);
}
