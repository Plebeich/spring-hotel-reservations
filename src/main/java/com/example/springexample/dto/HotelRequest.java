package com.example.springexample.dto;

import lombok.Data;

@Data
public class HotelRequest {
        private String name;
        private String title;
        private String city;
        private String address;
        private int center;
}
