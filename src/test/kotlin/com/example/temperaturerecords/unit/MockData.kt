package com.example.temperaturerecords.unit

import com.example.temperaturerecords.dto.WeatherRecordRequestDto
import com.example.temperaturerecords.entities.WeatherRecordEntity
import java.time.LocalDate

class MockData {

        companion object {
            fun getValidWeatherRecordRequestDto(): WeatherRecordRequestDto {
                return WeatherRecordRequestDto(
                    date = LocalDate.now(),
                    lat = 50.4501,
                    lon = 30.5234,
                    city = "Kiev",
                    state = "Kiev",
                    temperatures = listOf(20.5, 22.3, 19.8)
                )
            }
            fun getValidWeatherRecordEntity(): WeatherRecordEntity {
                return WeatherRecordEntity(
                    id = 1L,
                    date = LocalDate.now(),
                    lat = 50.4501,
                    lon = 30.5234,
                    city = "Kiev",
                    state = "Kiev",
                    temperatures = listOf(20.5, 22.3, 19.8)
                )
            }
        }
}