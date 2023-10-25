package com.example.temperaturerecords.integration

import com.example.temperaturerecords.controller.TemperatureRecordsController.Companion.WEATHER_URL
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get

class IntegrationIntegrationTest : AbstractIntegrationTest() {
    @Test
    fun shouldSuccessWhenGetByDateFilter() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            param("date", "2019-09-26")
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id", `is`(2))
            jsonPath("$[0].lon", `is`(34.0522))
            jsonPath("$[0].lat", `is`(-118.2437))
            jsonPath("$[0].city", `is`("Los Angeles"))
            jsonPath("$[0].state", `is`("CA"))
            jsonPath("$[0].date", `is`("2019-09-26"))
        }
    }

    @Test
    fun shouldSuccessGetByCityFilterRecords() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            param("cities", "San Francisco,nEw YoRk")
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id", `is`(1))
            jsonPath("$[0].lon", `is`(37.7749))
            jsonPath("$[0].lat", `is`(-122.4194))
            jsonPath("$[0].city", `is`("San Francisco"))
            jsonPath("$[0].state", `is`("CA"))
            jsonPath("$[0].date", `is`("2020-06-13"))
            jsonPath("$[1].id", `is`(3))
            jsonPath("$[1].lon", `is`(40.7128))
            jsonPath("$[1].lat", `is`(-74.0060))
            jsonPath("$[1].city", `is`("New York"))
            jsonPath("$[1].state", `is`("NY"))
            jsonPath("$[1].date", `is`("2015-03-07"))
        }
    }

    @Test
    fun shouldSuccessGetBySortFilter() {
        mockMvc?.get(WEATHER_URL) {
            contentType = MediaType.APPLICATION_JSON
            accept = MediaType.APPLICATION_JSON
            param("sort", "date")
        }?.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            jsonPath("$[0].id", `is`(3))
            jsonPath("$[0].lon", `is`(40.7128))
            jsonPath("$[0].lat", `is`(-74.0060))
            jsonPath("$[0].city", `is`("New York"))
            jsonPath("$[0].state", `is`("NY"))
            jsonPath("$[0].date", `is`("2015-03-07"))
            jsonPath("$[1].id", `is`(2))
            jsonPath("$[1].lon", `is`(34.0522))
            jsonPath("$[1].lat", `is`(-118.2437))
            jsonPath("$[1].city", `is`("Los Angeles"))
            jsonPath("$[1].state", `is`("CA"))
            jsonPath("$[1].date", `is`("2019-09-26"))
            jsonPath("$[2].id", `is`(1))
            jsonPath("$[2].lon", `is`(37.7749))
            jsonPath("$[2].lat", `is`(-122.4194))
            jsonPath("$[2].city", `is`("San Francisco"))
            jsonPath("$[2].state", `is`("CA"))
            jsonPath("$[2].date", `is`("2020-06-13"))
        }
    }

        @Test
        fun shouldSuccessGetById() {
            mockMvc?.get(WEATHER_URL.plus("/2")) {
                contentType = MediaType.APPLICATION_JSON
                accept = MediaType.APPLICATION_JSON
            }?.andExpect {
                status { isOk() }
                content { contentType(MediaType.APPLICATION_JSON) }
                jsonPath("$.lon", `is`(34.0522))
                jsonPath("$.lat", `is`(-118.2437))
                jsonPath("$.city", `is`("Los Angeles"))
                jsonPath("$.state", `is`("CA"))
                jsonPath("$.date", `is`("2019-09-26"))
        }
    }
}