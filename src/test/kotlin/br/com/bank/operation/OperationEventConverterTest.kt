package br.com.bank.operation

import br.com.bank.operation.account.Account
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.Transaction
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class OperationEventConverterTest {
    private lateinit var operationEventConverter: OperationEventConverter

    @Before
    fun init() {
        val mapper = ObjectMapper().registerModule(KotlinModule())
                .registerModule(JavaTimeModule())
                .enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

        operationEventConverter = OperationEventConverter(mapper)
    }

    @Test
    fun `should convert account message to AccountOperationEvent`() {
        val accountJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100} }"
        val operationEvent = operationEventConverter.convertEvents(listOf(accountJson)).first() as AccountOperationEvent

        assertEquals(true, operationEvent.activeCard)
        assertEquals(100, operationEvent.availableLimit)
    }

    @Test
    fun `should convert account message to TransactionOperationEvent`() {
        val transactionJson = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\" }}"
        val operationEvent = operationEventConverter.convertEvents(listOf(transactionJson)).first() as TransactionOperationEvent

        assertEquals("Burguer King", operationEvent.merchant)
        assertEquals(20, operationEvent.amount)
        assertEquals(ZonedDateTime.parse("2019-02-13T11:00:00.000Z[UTC]"), operationEvent.time)
    }
}