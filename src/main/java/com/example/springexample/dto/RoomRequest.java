package com.example.springexample.dto;

import lombok.Data;

@Data
public class RoomRequest {
    private String name;
    private String description;
    private String number;
    private double price;
    private int maxGuests;
    private Integer hotelId;
}
