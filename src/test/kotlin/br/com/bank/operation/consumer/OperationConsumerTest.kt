package br.com.bank.operation.consumer

import br.com.bank.infra.Reader
import br.com.bank.operation.OperationEventConverter
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class OperationConsumerTest {
    private lateinit var subject: OperationConsumer

    @Before
    fun init() {
        val accountJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100} }"
        val transactionJson = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\" }}"
        val events = listOf(accountJson, transactionJson)
        val reader: Reader = mockk(relaxed = true)

        every { reader.recursiveRead(any()) } returns events

        val mapper = ObjectMapper().registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        val operationEventConverter = OperationEventConverter(mapper)
        subject = OperationConsumer(operationEventConverter = operationEventConverter, reader = reader)
    }


    @Test
    fun `should consume events`() {
        val operationEvents = subject.consume()

        assertEquals(2, operationEvents.size)
        assertTrue(
                operationEvents.containsAll(listOf(
                        AccountOperationEvent(true, 100),
                        TransactionOperationEvent("Burguer King", 20, ZonedDateTime.parse("2019-02-13T11:00:00.000Z[UTC]"))
                ))
        )
    }
}