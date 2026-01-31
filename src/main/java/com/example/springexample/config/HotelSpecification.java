package com.example.springexample.config;

import com.example.springexample.dto.HotelFilter;
import com.example.springexample.model.Hotel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class HotelSpecification {
    public static Specification<Hotel> withFilter(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), filter.getId()));
            }

            if (StringUtils.hasText(filter.getName())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + filter.getName().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(filter.getTitle())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + filter.getTitle().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(filter.getCity())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("city")),
                        "%" + filter.getCity().toLowerCase() + "%"
                ));
            }

            if (StringUtils.hasText(filter.getAddress())) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("address")),
                        "%" + filter.getAddress().toLowerCase() + "%"
                ));
            }

            if (filter.getMinDistance() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("center"), filter.getMinDistance()
                ));
            }

            if (filter.getMaxDistance() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("center"), filter.getMaxDistance()
                ));
            }

            if (filter.getMinRating() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("rating"), filter.getMinRating()
                ));
            }

            if (filter.getMaxRating() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("rating"), filter.getMaxRating()
                ));
            }

            if (filter.getMinReviews() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("reviewsCount"), filter.getMinReviews()
                ));
            }

            if (filter.getMaxReviews() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("reviewsCount"), filter.getMaxReviews()
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
