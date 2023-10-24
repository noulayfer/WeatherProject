package com.example.temperaturerecords.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "weather_records")
public class WeatherRecordEntity {
    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "weather_records_sequence",
            sequenceName = "weather_records_sequence",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_records_sequence")
    private Long id;

    @JsonFormat(pattern = "YYYY-MM-DD")
    @Column(name = "date", nullable = false)
    private Instant date;

    @Column(name = "lon", nullable = false)
    private Double lon;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state")
    private String state;

    @ElementCollection
    @CollectionTable(name = "temperatures", joinColumns = @JoinColumn(name = "weather_data_id"))
    @Column(name = "temperature", nullable = false)
    @BatchSize(size = 10)
    private List<Float> temperatures;
}
