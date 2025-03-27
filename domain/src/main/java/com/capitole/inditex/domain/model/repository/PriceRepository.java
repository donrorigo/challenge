package com.capitole.inditex.domain.model.repository;

import com.capitole.inditex.domain.model.entity.Price;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {
  Optional<Price> findApplicablePrice(Long brandId, Long productId, LocalDateTime date);
}
