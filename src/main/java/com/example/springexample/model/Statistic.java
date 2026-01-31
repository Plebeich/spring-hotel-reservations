package com.example.springexample.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "statistic_events")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "event_type", nullable = false)
    private String eventType; // "user registered" или "room booked"    переделать в enum

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "check_in_date")
    private String checkInDate;

    @Column(name = "check_out_date")
    private String checkOutDate;

    @Column(name = "additional_data", columnDefinition = "TEXT")
    private String additionalData;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

}
