package com.example.springexample.config;

import com.example.springexample.dto.RoomFilter;
import com.example.springexample.model.Booking;
import com.example.springexample.model.Room;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class RoomSpecification {
    public static Specification<Room> withFilter(RoomFilter filter) {
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

            if (filter.getMinPrice() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"), filter.getMinPrice()
                ));
            }

            if (filter.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"), filter.getMaxPrice()
                ));
            }

            if (filter.getMinGuests() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("maxGuests"), filter.getMinGuests()
                ));
            }

            if (filter.getMaxGuests() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("maxGuests"), filter.getMaxGuests()
                ));
            }

            if (filter.getHotelId() != null) {
                predicates.add(criteriaBuilder.equal(
                        root.get("hotel").get("id"), filter.getHotelId()
                ));
            }

            if (filter.getCheckInDate() != null && filter.getCheckOutDate() != null) {
                if (filter.getCheckOutDate().isAfter(filter.getCheckInDate())) {

                    Subquery<Long> subquery = query.subquery(Long.class);
                    Root<Booking> bookingRoot = subquery.from(Booking.class);

                    Predicate overlappingDates = criteriaBuilder.and(
                            criteriaBuilder.equal(bookingRoot.get("room").get("id"), root.get("id")),
                            criteriaBuilder.lessThan(bookingRoot.get("checkInDate"), filter.getCheckOutDate()),
                            criteriaBuilder.greaterThan(bookingRoot.get("checkOutDate"), filter.getCheckInDate())
                    );

                    subquery.select(criteriaBuilder.literal(1L))
                            .where(overlappingDates);

                    predicates.add(criteriaBuilder.not(criteriaBuilder.exists(subquery)));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
