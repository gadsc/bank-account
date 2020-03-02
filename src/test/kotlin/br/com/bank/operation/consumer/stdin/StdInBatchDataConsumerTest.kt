package br.com.bank.operation.consumer.stdin

import br.com.bank.infra.CustomObjectMapper
import br.com.bank.infra.Reader
import br.com.bank.operation.OperationEventConverter
import br.com.bank.operation.consumer.OperationConsumer
import br.com.bank.operation.objectMother.AccountOperationEventObjectMother
import br.com.bank.operation.objectMother.TransactionOperationEventObjectMother
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class StdInBatchDataConsumerTest {
    private lateinit var subject: StdInBatchDataConsumer
    private val accountEvent = AccountOperationEventObjectMother.build()
    private val transactionEvent = TransactionOperationEventObjectMother.build()

    @Before
    fun init() {
        val accountJson = "{\"account\": {\"active-card\": true, \"available-limit\": 100} }"
        val transactionJson = "{\"transaction\": {\"merchant\": \"Burguer King\", \"amount\": 20, \"time\": \"2019-02-13T11:00:00.000Z\" }}"

        val events = listOf(accountJson, transactionJson)
        val reader: Reader = mockk(relaxed = true)
        val operationEventConverter = OperationEventConverter(mapper = CustomObjectMapper.mapper)
        val operationConsumer = OperationConsumer(
                operationEventConverter = operationEventConverter,
                reader = reader
        )

        every { reader.recursiveRead(any()) } returns events

        subject = StdInBatchDataConsumer(operationConsumer)
    }

    @Test
    fun `should return executed operations`() {
        val results = subject.process()

        assertEquals(2, results.size)
        assertEquals(accountEvent.activeCard, results[0].account?.activeCard)
        assertEquals(accountEvent.availableLimit, results[0].account?.availableLimit)
        assertEquals(accountEvent.activeCard, results[1].account?.activeCard)
        assertEquals(accountEvent.availableLimit - transactionEvent.amount, results[1].account?.availableLimit)
    }
}