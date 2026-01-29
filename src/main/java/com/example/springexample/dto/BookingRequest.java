package com.example.springexample.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BookingRequest {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;
    @NotNull
    private int roomId;

    @NotNull
    private Integer userId;

    public boolean isValid() {
        return checkInDate != null && checkOutDate != null && checkOutDate.isAfter(checkInDate);
    }
}
