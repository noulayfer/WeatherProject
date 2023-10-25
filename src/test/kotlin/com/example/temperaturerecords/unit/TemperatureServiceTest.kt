package com.example.temperaturerecords.unit

import com.example.temperaturerecords.mapper.WeatherMapper
import com.example.temperaturerecords.repository.TemperatureRepository
import com.example.temperaturerecords.service.TemperatureService
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.persistence.EntityManager

@SpringBootTest
class TemperatureServiceTest {

    private val temperatureRepository: TemperatureRepository = mockk<TemperatureRepository>()
    private val weatherMapper: WeatherMapper = spyk<WeatherMapper>()
    private val entityManager: EntityManager = mockk<EntityManager>()
    private val weatherRecordService = TemperatureService(temperatureRepository, weatherMapper, entityManager)

    @Test
    fun `test createWeatherRecord with valid input`() {
        // Arrange
        val weatherRecordRequestDto = MockData.getValidWeatherRecordRequestDto()
        val weatherRecordEntity = MockData.getValidWeatherRecordEntity()
        every { temperatureRepository.save(any()) } returns weatherRecordEntity
        // Act
        val result = weatherRecordService.createWeatherRecord(weatherRecordRequestDto)

        // Assert
        assertEquals(weatherRecordEntity, result)
    }
}
