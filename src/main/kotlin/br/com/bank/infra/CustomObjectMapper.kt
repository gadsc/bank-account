package br.com.bank.infra

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule

object CustomObjectMapper {
    val mapper: ObjectMapper = ObjectMapper().registerModule(KotlinModule())
            .registerModule(JavaTimeModule())
            .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
}
