package com.example.springexample.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {
    @NotNull
    @Min(value = 1, message = "Рейтинг не < 1")
    @Max(value = 5, message = "Рейтинг не > 5")
    private Integer rating;
}
