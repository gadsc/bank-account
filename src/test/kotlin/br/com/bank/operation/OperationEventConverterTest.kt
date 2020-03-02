package br.com.bank.operation

import br.com.bank.infra.CustomObjectMapper
import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class OperationEventConverterTest {
    private lateinit var operationEventConverter: OperationEventConverter

    @Before
    fun init() {
        operationEventConverter = OperationEventConverter(CustomObjectMapper.mapper)
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