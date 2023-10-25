package com.example.temperaturerecords.repository

import com.example.temperaturerecords.entities.WeatherRecordEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TemperatureRepository : JpaRepository<WeatherRecordEntity, Long>
