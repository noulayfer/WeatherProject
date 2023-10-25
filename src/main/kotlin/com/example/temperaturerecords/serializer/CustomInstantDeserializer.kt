package com.example.temperaturerecords.serializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.io.IOException
import java.time.LocalDate

class CustomInstantDeserializer : JsonDeserializer<LocalDate>() {
    @Throws(IOException::class, IOException::class)
    override fun deserialize(jsonParser: JsonParser, deserializationContext: DeserializationContext): LocalDate {
        val dateString = jsonParser.text
        return LocalDate.parse(dateString)
    }
}