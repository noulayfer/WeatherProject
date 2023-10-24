package com.example.temperaturerecords.repository;

import com.example.temperaturerecords.entities.WeatherRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperatureRepository extends JpaRepository<WeatherRecordEntity, Long> {
}
