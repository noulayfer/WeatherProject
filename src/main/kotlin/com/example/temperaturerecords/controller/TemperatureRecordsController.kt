package com.example.temperaturerecords.controller

import com.example.temperaturerecords.dto.WeatherRecordRequestDto
import com.example.temperaturerecords.entities.WeatherRecordEntity
import com.example.temperaturerecords.service.TemperatureService
import com.example.temperaturerecords.util.filter.WeatherRecordFilter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping(TemperatureRecordsController.WEATHER_URL)
class TemperatureRecordsController(
    private val temperatureService: TemperatureService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWeatherDataRecord(@RequestBody weatherRecordRequestDto: @Valid WeatherRecordRequestDto): WeatherRecordEntity {
        return temperatureService.createWeatherRecord(weatherRecordRequestDto)
    }

    @GetMapping
    fun getWeatherDataRecords(weatherRecordFilter: WeatherRecordFilter): List<WeatherRecordEntity?>? {
        return temperatureService.getWeatherRecordsWithFilter(weatherRecordFilter)
    }

    @GetMapping(WEATHER_ID_URI)
    fun getWeatherDataRecordById(@PathVariable id: Long): WeatherRecordEntity {
        return temperatureService.getWeatherDataRecordById(id)
    }

    companion object {
        private const val API_URI = "/api"
        private const val WEATHER_URI = "/weather"
        const val WEATHER_ID_URI = "/{id}"
        const val WEATHER_URL = API_URI + WEATHER_URI
    }
}
