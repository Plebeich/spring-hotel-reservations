package com.example.springexample.dto;


import lombok.Data;

@Data
public class HotelFilter {
    private Integer id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Double minDistance;
    private Double maxDistance;
    private Double minRating;
    private Double maxRating;
    private Integer minReviews;
    private Integer maxReviews;
}
