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

    @Test
    fun `should return TransactionOperationEvents ordered by time`() {
        val thirdTransaction = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\" }}"
        val secondTransaction = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-12T11:00:00.000Z\" }}"
        val firstTransaction = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-11T11:00:00.000Z\" }}"
        val fourthTransaction = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-14T11:00:00.000Z\" }}"
        val operationEvent = operationEventConverter.convertEvents(listOf(thirdTransaction, secondTransaction, firstTransaction, fourthTransaction)).map { it as TransactionOperationEvent }

        assertEquals(ZonedDateTime.parse("2019-02-11T11:00:00.000Z[UTC]"), operationEvent[0].time)
        assertEquals(ZonedDateTime.parse("2019-02-12T11:00:00.000Z[UTC]"), operationEvent[1].time)
        assertEquals(ZonedDateTime.parse("2019-02-13T11:00:00.000Z[UTC]"), operationEvent[2].time)
        assertEquals(ZonedDateTime.parse("2019-02-14T11:00:00.000Z[UTC]"), operationEvent[3].time)
    }
}