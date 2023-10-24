package com.example.temperaturerecords.dto;

import lombok.Builder;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Builder
public record WeatherRecordRequestDto(
        Instant instant,
        @NotNull
        @DecimalMin(value = "-90.0", message = "Inviable number was entered. Try again.")
        @DecimalMax(value = "90.0", message = "Inviable number was entered. Try again.")
        @Digits(integer = 3, fraction = 6, message = "Inappropriate format was used. Try again.")
        Double lat,
        @DecimalMin(value = "-180.0", message = "Inviable number was entered. Try again.")
        @DecimalMax(value = "180.0", message = "Inviable number was entered. Try again.")
        @Digits(integer = 3, fraction = 6, message = "Inappropriate format was used. Try again.")
        @NotNull
        Double lon,
        String city,
        String state,
        List<Float> temperatures
) {}
