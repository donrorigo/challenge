package com.capitole.inditex.infrastructure.h2.repository;

import com.capitole.inditex.infrastructure.h2.entity.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface H2PriceRepository extends JpaRepository<PriceEntity, Long>,
    JpaSpecificationExecutor<PriceEntity> {


  boolean existsByBrandIdAndProductId(Long brandId, Long productId);
}
