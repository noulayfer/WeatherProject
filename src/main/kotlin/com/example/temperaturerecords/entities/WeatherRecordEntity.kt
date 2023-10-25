package com.example.temperaturerecords.entities

import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import java.time.LocalDate
import javax.persistence.CollectionTable
import javax.persistence.Column
import javax.persistence.ElementCollection
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.SequenceGenerator
import javax.persistence.Table


@Entity
@Table(name = "weather_records")
data class WeatherRecordEntity(
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "weather_records_sequence", sequenceName = "weather_records_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_records_sequence")
    var id: Long? = null,

    @Column(name = "date", nullable = false)
    var date: LocalDate? = null,

    @Column(name = "lon", nullable = false)
    var lon: Double? = null,

    @Column(name = "lat", nullable = false)
    var lat: Double? = null,

    @Column(name = "city", nullable = false)
    var city: String? = null,

    @Column(name = "state")
    var state: String? = null,

    @ElementCollection
    @CollectionTable(name = "temperatures", joinColumns = [JoinColumn(name = "weather_data_id")])
    @Column(name = "temperature", nullable = false)
    @BatchSize(size = 10)
    @Cascade(CascadeType.DELETE)
    var temperatures: List<Double>? = null
)
