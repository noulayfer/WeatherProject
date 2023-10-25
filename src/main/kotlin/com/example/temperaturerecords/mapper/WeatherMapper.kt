package com.example.temperaturerecords.mapper

import com.example.temperaturerecords.dto.WeatherRecordRequestDto
import com.example.temperaturerecords.entities.WeatherRecordEntity
import org.springframework.stereotype.Component

@Component
class WeatherMapper {
    fun weatherRecordRequestDtoToEntity(weatherRecordRequestDto: WeatherRecordRequestDto?): WeatherRecordEntity? {
        return WeatherRecordEntity(
            date = weatherRecordRequestDto?.date,
            temperatures = weatherRecordRequestDto?.temperatures,
            state = weatherRecordRequestDto?.state,
            city = weatherRecordRequestDto?.city,
            lat = weatherRecordRequestDto?.lat,
            lon = weatherRecordRequestDto?.lon
        )
    }
}
