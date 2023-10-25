package com.example.temperaturerecords.service

import com.example.temperaturerecords.dto.WeatherRecordRequestDto
import com.example.temperaturerecords.entities.WeatherRecordEntity
import com.example.temperaturerecords.mapper.WeatherMapper
import com.example.temperaturerecords.repository.TemperatureRepository
import com.example.temperaturerecords.util.filter.WeatherRecordFilter
import org.springframework.stereotype.Service
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.persistence.EntityNotFoundException
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Service
class TemperatureService(val temperatureRepository:TemperatureRepository,
    val weatherMapper: WeatherMapper,
                         val entityManager: EntityManager) {
    fun createWeatherRecord(weatherRecordRequestDto: WeatherRecordRequestDto?): WeatherRecordEntity {
        return temperatureRepository.save(weatherMapper.weatherRecordRequestDtoToEntity(weatherRecordRequestDto)!!)
    }

    fun getWeatherRecordsWithFilter(weatherRecordFilter: WeatherRecordFilter): List<WeatherRecordEntity> {
        val builder = entityManager.criteriaBuilder
        val query = builder.createQuery(WeatherRecordEntity::class.java)
        val root: Root<WeatherRecordEntity> = query.from(WeatherRecordEntity::class.java)
        val predicates: MutableList<Predicate> = mutableListOf()

        weatherRecordFilter.date?.let { date ->
            val datePredicate: Predicate = builder.equal(root.get<LocalDate>("date"), LocalDate.parse(date))
            predicates.add(datePredicate)
        }

        val cities = weatherRecordFilter.cities.orEmpty()
        if (cities.isNotEmpty()) {
            val cityPredicate: Predicate = builder.lower(root.get("city")).`in`(cities.map { it.lowercase() })
            predicates.add(cityPredicate)
        }

        query.where(*predicates.toTypedArray())
        when (weatherRecordFilter.sort) {
            "date" -> {
                query.orderBy(builder.asc(root.get<LocalDate>("date")), builder.asc(root.get<Long>("id")))
            }
            "-date" -> {
                query.orderBy(builder.desc(root.get<LocalDate>("date")), builder.asc(root.get<Long>("id")))
            }
            else -> {
                query.orderBy(builder.asc(root.get<Long>("id")))
            }
        }

        val typedQuery = entityManager.createQuery(query)
        return typedQuery.resultList
    }

    fun getWeatherDataRecordById(id: Long): WeatherRecordEntity {
        return temperatureRepository
            .findById(id).orElseThrow { EntityNotFoundException() }!!
    }
}
