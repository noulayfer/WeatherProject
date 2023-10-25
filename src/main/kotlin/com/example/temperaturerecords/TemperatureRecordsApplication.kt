package com.example.temperaturerecords

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class TemperatureRecordsApplication

fun main(args: Array<String>) {
    runApplication<TemperatureRecordsApplication>(*args)
}

