package br.com.bank.operation.consumer.stdin

import br.com.bank.operation.account.AccountOperationEvent
import br.com.bank.operation.account.transaction.TransactionOperationEvent
import br.com.bank.operation.consumer.OperationConsumer
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.ZonedDateTime

class StdInDataConsumerTest {
    private lateinit var subject: StdInDataConsumer
    private val accountEvent = AccountOperationEvent(true, 100)
    private val transactionEvent = TransactionOperationEvent("Burguer King", 20, ZonedDateTime.parse("2019-02-13T11:00:00.000Z[UTC]"))

    @Before
    fun init() {
        val events = listOf(accountEvent, transactionEvent)

        val operationConsumer: OperationConsumer = mockk(relaxed = true)

        every { operationConsumer.consume() } returns events

        subject = StdInDataConsumer(operationConsumer)
    }

    @Test
    fun `should return executed operations`() {
        val results = subject.batchProcessing()

        assertEquals(2, results.size)
        assertEquals(accountEvent.activeCard, results[0].account?.activeCard)
        assertEquals(accountEvent.availableLimit, results[0].account?.availableLimit)
        assertEquals(accountEvent.activeCard, results[1].account?.activeCard)
        assertEquals(accountEvent.availableLimit - transactionEvent.amount, results[1].account?.availableLimit)
    }
}