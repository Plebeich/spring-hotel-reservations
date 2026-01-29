package com.example.springexample.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomFilter {
    private Integer id;
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Integer minGuests;
    private Integer maxGuests;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer hotelId;
}
