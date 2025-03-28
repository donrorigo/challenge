package com.capitole.inditex.infrastructure.h2.mapper;

import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.infrastructure.h2.entity.PriceEntity;
import java.util.Collection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepositoryMapper {

  Price toDomain(PriceEntity entity);

  Collection<Price> toDomainCollection(Collection<PriceEntity> entities);

}
