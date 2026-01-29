package com.example.springexample.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingResponse {
    private int id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDate createdAt;
    private int roomId;
    private String roomName;
    private String roomNumber;
    private int hotelId;
    private String hotelName;
    private int userId;
    private String username;
    private String userEmail;
}
