package com.example.temperaturerecords.dto

import com.example.temperaturerecords.serializer.CustomInstantDeserializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.time.LocalDate
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits
import javax.validation.constraints.NotNull

data class WeatherRecordRequestDto(
        @JvmField @field:JsonDeserialize(using = CustomInstantDeserializer::class) @param:JsonDeserialize(
        using = CustomInstantDeserializer::class
    ) val date: LocalDate,
        @JvmField val lat: @NotNull @DecimalMin(
        value = "-90.0",
        message = "Inviable number was entered. Try again."
    ) @DecimalMax(
        value = "90.0",
        message = "Inviable number was entered. Try again."
    ) @Digits(
        integer = 3,
        fraction = 6,
        message = "Inappropriate format was used. Try again."
    ) Double?,
        @JvmField val lon: @DecimalMin(
        value = "-180.0",
        message = "Inviable number was entered. Try again."
    ) @DecimalMax(
        value = "180.0",
        message = "Inviable number was entered. Try again."
    ) @Digits(
        integer = 3,
        fraction = 6,
        message = "Inappropriate format was used. Try again."
    ) @NotNull Double?,
        @JvmField val city: String,
        @JvmField val state: String,
        @JvmField val temperatures: List<Double>
)
