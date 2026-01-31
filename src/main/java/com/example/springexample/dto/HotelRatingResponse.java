package com.example.springexample.dto;

import lombok.Data;

@Data
public class HotelRatingResponse {
    private Double currentRating;
    private Integer numberRatings;
    private Integer hotelId;
    private String hotelName;
}
