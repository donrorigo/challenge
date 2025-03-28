package com.capitole.inditex.infrastructure.h2.repository;

import com.capitole.inditex.domain.model.valueobject.PriceFilter;
import com.capitole.inditex.infrastructure.h2.entity.PriceEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public final class PriceSpecification {

  private PriceSpecification() {
    throw new IllegalStateException();
  }

  public static Specification<PriceEntity> withFilters(PriceFilter filters) {
    return (root, query, criteriaBuilder) -> {
      Predicate predicate = criteriaBuilder.conjunction();

      predicate = filterByApplicationDate(filters, root, criteriaBuilder, predicate);
      predicate = filterByBrand(filters, root, criteriaBuilder, predicate);
      predicate = filterByProduct(filters, root, criteriaBuilder, predicate);

      return predicate;
    };
  }




  private static Predicate filterByProduct(PriceFilter filters, Root<PriceEntity> root,
      CriteriaBuilder criteriaBuilder, Predicate predicate) {
    if (filters.productId() != null) {
      predicate = criteriaBuilder.and(predicate,
          criteriaBuilder.equal(root.get("productId"), filters.productId()));
    }
    return predicate;
  }

  private static Predicate filterByBrand(PriceFilter filters, Root<PriceEntity> root,
      CriteriaBuilder criteriaBuilder, Predicate predicate) {
    if (filters.brandId() != null) {
      predicate = criteriaBuilder.and(predicate,
          criteriaBuilder.equal(root.get("brandId"), filters.brandId()));
    }
    return predicate;
  }

  private static Predicate filterByApplicationDate(PriceFilter filters, Root<PriceEntity> root,
      CriteriaBuilder criteriaBuilder, Predicate predicate) {
    if (filters.applicationDate() != null) {
      Predicate startDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), filters.applicationDate());
      Predicate endDatePredicate = criteriaBuilder.greaterThanOrEqualTo(
          root.get("endDate"), filters.applicationDate());

      predicate = criteriaBuilder.and(predicate, startDatePredicate, endDatePredicate);
    }
    return predicate;
  }
}
