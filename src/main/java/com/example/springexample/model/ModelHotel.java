package com.example.springexample.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "hotels")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelHotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String name;
    @Column(name = "title",nullable = false,columnDefinition = "VARCHAR(255)")
    private String title;
    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String city;
    @Column(nullable = false,columnDefinition = "VARCHAR(255)")
    private String address;
    @Column(nullable = false)
    private int center;
    @Column(nullable = false)
    private double rating = 0;
    @Column(name = "reviews_count",nullable = false)
    private int reviewsCount = 0;
}
