package com.example.temperaturerecords.controller;

import com.example.temperaturerecords.dto.WeatherRecordRequestDto;
import com.example.temperaturerecords.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/weather")
public class TemperatureRecordsController {
    private final TemperatureService temperatureService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WeatherRecordRequestDto createWeatherDataRecord
            (@Valid @RequestBody WeatherRecordRequestDto weatherRecordRequestDto) {
        return null;
    }
}
