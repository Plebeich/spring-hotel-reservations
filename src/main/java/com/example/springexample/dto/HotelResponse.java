package com.example.springexample.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class HotelResponse {
    private int id;
    private String name;
    private String title;
    private String city;
    private String address;
    private int center;
    private double rating;
    private int reviewsCount;
}
