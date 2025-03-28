package com.capitole.inditex.infrastructure.rest.mapper;

import com.capitole.inditex.domain.model.entity.Price;
import com.capitole.inditex.infrastructure.rest.model.PriceDto;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import org.joda.money.CurrencyUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ControllerMapper {

  @Mapping(target = "price", source = "money.amount")
  @Mapping(target = "currency", source = "money.currencyUnit")
  PriceDto toDto(Price price);

  List<PriceDto> toDtoCollection(Collection<Price> prices);

  default OffsetDateTime map(LocalDateTime value){
    return value.atOffset(ZoneOffset.UTC);
  }

  default String map(CurrencyUnit value){
    return value.getCode();
  }
}
