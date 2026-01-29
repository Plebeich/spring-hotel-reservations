package com.example.springexample.dto;

import lombok.Data;

@Data
public class RoomResponse {
    private int id;
    private String name;
    private String description;
    private String number;
    private double price;
    private int maxGuests;
    private int hotelId;
    private String hotelName;
}
