package com.capitole.inditex.application.ports.output;

import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.domain.model.valueobject.PriceFilter;
import java.util.Collection;

public interface PriceRepository {
  Collection<Price> findApplicablePrice(PriceFilter filter);
}
